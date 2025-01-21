package org.artemzhuravlov.clarity.di

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import dev.jordond.compass.geolocation.Geolocator
import dev.jordond.compass.geolocation.mobile
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.artemzhuravlov.clarity.data.repository.login.ILoginRepository
import org.artemzhuravlov.clarity.data.repository.places.IPlacesRepository
import org.artemzhuravlov.clarity.data.repository.places.PlacesRepository
import org.artemzhuravlov.clarity.data.repository.settings.SettingsRepository
import org.artemzhuravlov.clarity.navigation.login.ILoginScreenComponent
import org.artemzhuravlov.clarity.navigation.login.DefaultLoginScreenComponent
import org.artemzhuravlov.clarity.navigation.main.IMainScreenComponent
import org.artemzhuravlov.clarity.navigation.main.DefaultMainScreenComponent
import org.artemzhuravlov.clarity.navigation.maps.clinic_details.IClinicDetailsScreenComponent
import org.artemzhuravlov.clarity.navigation.maps.clinic_details.DefaultClinicDetailsScreenComponent
import org.artemzhuravlov.clarity.navigation.maps.clinics_map.IClinicsMapScreenComponent
import org.artemzhuravlov.clarity.navigation.maps.clinics_map.DefaultClinicsMapScreenComponent
import org.artemzhuravlov.clarity.navigation.maps.fullscreen_map.IMapScreenComponent
import org.artemzhuravlov.clarity.navigation.maps.fullscreen_map.DefaultMapScreenComponent
import org.artemzhuravlov.clarity.navigation.profile.IProfileScreenComponent
import org.artemzhuravlov.clarity.navigation.profile.DefaultProfileScreenComponent
import org.artemzhuravlov.clarity.navigation.root.DefaultRootComponent
import org.artemzhuravlov.clarity.navigation.root.IRootComponent
import org.artemzhuravlov.clarity.data.repository.login.LoginRepository
import org.artemzhuravlov.clarity.data.repository.config.FirebaseConfigRepository
import org.artemzhuravlov.clarity.data.repository.config.IConfigRepository
import org.artemzhuravlov.clarity.data.repository.documents.DocumentsRepository
import org.artemzhuravlov.clarity.data.repository.documents.IDocumentsRepository
import org.artemzhuravlov.clarity.data.repository.main.IUserDataRepository
import org.artemzhuravlov.clarity.data.repository.main.UserDataRepository
import org.artemzhuravlov.clarity.data.repository.policies.IPoliciesRepository
import org.artemzhuravlov.clarity.data.repository.policies.PoliciesRepository
import org.artemzhuravlov.clarity.data.repository.user.FirebaseUserRepository
import org.artemzhuravlov.clarity.data.repository.user.IUserRepository
import org.artemzhuravlov.clarity.navigation.contacts.IContactsScreenComponent
import org.artemzhuravlov.clarity.navigation.contacts.DefaultContactsScreenComponent
import org.artemzhuravlov.clarity.navigation.maps.clincs_list.DefaultClinicsListScreenComponent
import org.artemzhuravlov.clarity.navigation.maps.clincs_list.IClinicsListScreenComponent
import org.artemzhuravlov.clarity.navigation.policies.DefaultPoliciesScreenComponent
import org.artemzhuravlov.clarity.navigation.policies.IPoliciesScreenComponent
import org.artemzhuravlov.clarity.navigation.privacy_policy.IPrivacyPolicyScreenComponent
import org.artemzhuravlov.clarity.navigation.privacy_policy.DefaultPrivacyPolicyScreenComponent
import org.artemzhuravlov.clarity.navigation.terms_and_conditions.DefaultTermsAndConditionsScreenComponent
import org.artemzhuravlov.clarity.navigation.terms_and_conditions.ITermsAndConditionsScreenComponent
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

val koin by lazy { initKoin().koin }

fun initKoin(appDeclaration: KoinAppDeclaration? = null) = startKoin {
    appDeclaration?.invoke(this)
    modules(appModule, platformModule, networkModule, repositoryModule, sharedModule)
}

expect val platformModule: Module

val appModule = module {
    single<ILoginScreenComponent.Factory> {
        DefaultLoginScreenComponent.Factory(
            loginRepository = get(),
        )
    }

    single<IMainScreenComponent.Factory> {
        DefaultMainScreenComponent.Factory(
            userDataRepository = get()
        )
    }

    single<IClinicsMapScreenComponent.Factory> {
        DefaultClinicsMapScreenComponent.Factory(
            placesRepository = get(),
            geoLocation = get()
        )
    }

    single<IClinicsListScreenComponent.Factory> {
        DefaultClinicsListScreenComponent.Factory(
            placesRepository = get()
        )
    }

    single<IClinicDetailsScreenComponent.Factory> {
        DefaultClinicDetailsScreenComponent.Factory(
            placesRepository = get()
        )
    }

    single<IMapScreenComponent.Factory> {
        DefaultMapScreenComponent.Factory()
    }

    single<IContactsScreenComponent.Factory> {
        DefaultContactsScreenComponent.Factory()
    }

    single<IProfileScreenComponent.Factory> {
        DefaultProfileScreenComponent.Factory(loginRepository = get(), userRepository = get())
    }

    single<IPrivacyPolicyScreenComponent.Factory> {
        DefaultPrivacyPolicyScreenComponent.Factory(configRepository = get())
    }

    single<ITermsAndConditionsScreenComponent.Factory> {
        DefaultTermsAndConditionsScreenComponent.Factory(configRepository = get())
    }

    single<IPoliciesScreenComponent.Factory> {
        DefaultPoliciesScreenComponent.Factory(
            policiesRepository = get(),
            documentsRepository = get()
        )
    }

    single<IRootComponent.Factory> {
        DefaultRootComponent.Factory(
            loginComponentFactory = get(),
            mainComponentFactory = get(),
            clinicsListComponentFactory = get(),
            clinicsMapComponentFactory = get(),
            clinicDetailsComponentFactory = get(),
            mapScreenComponent = get(),
            profileScreenComponent = get(),
            contactsScreenComponent = get(),
            privacyPolicyScreenComponent = get(),
            termsAndConditionsScreenComponent = get(),
            policiesScreenComponent = get(),
        )
    }
}

val networkModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                })

            }
        }
    }

    single {
        Firebase.auth
    }

    single {
        Firebase.firestore
    }
}

val repositoryModule = module {
    single<ILoginRepository> {
        LoginRepository(get())
    }

    single<SettingsRepository> {
        SettingsRepository(get())
    }

    single<IPlacesRepository> {
        // There should be your Google MAP Places API key
        PlacesRepository(client = get(), apiKey = "")
    }

    single<IUserRepository> { FirebaseUserRepository() }

    single<IConfigRepository> { FirebaseConfigRepository() }

    single<IPoliciesRepository> { PoliciesRepository() }

    single<IDocumentsRepository> { DocumentsRepository() }

    single<IUserDataRepository> { UserDataRepository() }
}

val sharedModule = module {
    single<Geolocator> {
        Geolocator.mobile()
    }
}
