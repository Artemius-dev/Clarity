package org.artemzhuravlov.clarity.navigation.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.popTo
import com.arkivanov.decompose.router.stack.replaceAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.serialization.Serializable
import org.artemzhuravlov.clarity.data.model.place.LatLng
import org.artemzhuravlov.clarity.data.model.place.PlaceInfo
import org.artemzhuravlov.clarity.navigation.contacts.IContactsScreenComponent
import org.artemzhuravlov.clarity.navigation.root.IRootComponent.*
import org.artemzhuravlov.clarity.navigation.login.ILoginScreenComponent
import org.artemzhuravlov.clarity.navigation.main.IMainScreenComponent
import org.artemzhuravlov.clarity.navigation.maps.clincs_list.IClinicsListScreenComponent
import org.artemzhuravlov.clarity.navigation.maps.clinic_details.IClinicDetailsScreenComponent
import org.artemzhuravlov.clarity.navigation.maps.clinics_map.IClinicsMapScreenComponent
import org.artemzhuravlov.clarity.navigation.maps.fullscreen_map.IMapScreenComponent
import org.artemzhuravlov.clarity.navigation.policies.IPoliciesScreenComponent
import org.artemzhuravlov.clarity.navigation.privacy_policy.IPrivacyPolicyScreenComponent
import org.artemzhuravlov.clarity.navigation.profile.IProfileScreenComponent
import org.artemzhuravlov.clarity.navigation.terms_and_conditions.ITermsAndConditionsScreenComponent

class DefaultRootComponent(
    componentContext: ComponentContext,
    private val loginComponentFactory: ILoginScreenComponent.Factory,
    private val mainComponentFactory: IMainScreenComponent.Factory,
    private val clinicsMapComponentFactory: IClinicsMapScreenComponent.Factory,
    private val clinicsListComponentFactory: IClinicsListScreenComponent.Factory,
    private val clinicDetailsComponentFactory: IClinicDetailsScreenComponent.Factory,
    private val mapScreenComponent: IMapScreenComponent.Factory,
    private val profileScreenComponent: IProfileScreenComponent.Factory,
    private val contactsScreenComponent: IContactsScreenComponent.Factory,
    private val privacyPolicyScreenComponent: IPrivacyPolicyScreenComponent.Factory,
    private val termsAndConditionsScreenComponent: ITermsAndConditionsScreenComponent.Factory,
    private val policiesScreenComponent: IPoliciesScreenComponent.Factory,
) : IRootComponent, ComponentContext by componentContext {

    class Factory(
        private val loginComponentFactory: ILoginScreenComponent.Factory,
        private val mainComponentFactory: IMainScreenComponent.Factory,
        private val clinicsMapComponentFactory: IClinicsMapScreenComponent.Factory,
        private val clinicsListComponentFactory: IClinicsListScreenComponent.Factory,
        private val clinicDetailsComponentFactory: IClinicDetailsScreenComponent.Factory,
        private val mapScreenComponent: IMapScreenComponent.Factory,
        private val profileScreenComponent: IProfileScreenComponent.Factory,
        private val contactsScreenComponent: IContactsScreenComponent.Factory,
        private val privacyPolicyScreenComponent: IPrivacyPolicyScreenComponent.Factory,
        private val termsAndConditionsScreenComponent: ITermsAndConditionsScreenComponent.Factory,
        private val policiesScreenComponent: IPoliciesScreenComponent.Factory,
    ) : IRootComponent.Factory {
        override fun invoke(componentContext: ComponentContext): DefaultRootComponent {
            return DefaultRootComponent(
                componentContext = componentContext,
                loginComponentFactory = loginComponentFactory,
                mainComponentFactory = mainComponentFactory,
                clinicsMapComponentFactory = clinicsMapComponentFactory,
                clinicsListComponentFactory = clinicsListComponentFactory,
                clinicDetailsComponentFactory = clinicDetailsComponentFactory,
                mapScreenComponent = mapScreenComponent,
                profileScreenComponent = profileScreenComponent,
                contactsScreenComponent = contactsScreenComponent,
                privacyPolicyScreenComponent = privacyPolicyScreenComponent,
                termsAndConditionsScreenComponent = termsAndConditionsScreenComponent,
                policiesScreenComponent = policiesScreenComponent,
            )
        }
    }

    private val navigation = StackNavigation<Configuration>()
    override val childStack = childStack(
        source = navigation,
        serializer = Configuration.serializer(),
        initialConfiguration = Configuration.LoginScreen,
        handleBackButton = true,
        childFactory = ::createChild
    )

    override fun navigateToMain() {
        navigation.popTo(0)
    }

    override fun navigateToClinicsMap() {
        navigation.popTo(0)
        navigation.bringToFront(Configuration.ClinicsMapScreen)
    }

    override fun navigateToProfile() {
        navigation.popTo(0)
        navigation.bringToFront(Configuration.ProfileScreen)
    }

    private fun createChild(
        config: Configuration,
        context: ComponentContext
    ): Child {
        return when (config) {
            Configuration.LoginScreen -> Child.LoginScreen(
                loginComponentFactory(
                    componentContext = context,
                    mainContext = Dispatchers.Main,
                    ioContext = Dispatchers.IO,
                    onNavigateToMainScreen = {
                        navigation.replaceAll(Configuration.MainScreen)
                    }
                )
            )

            Configuration.MainScreen -> Child.MainScreen(
                mainComponentFactory(
                    componentContext = context,
                    mainContext = Dispatchers.Main,
                    ioContext = Dispatchers.IO,
                    onNavigateToContactsScreen = {
                        navigation.bringToFront(Configuration.ContactsScreen)
                    },
                    onNavigateToInsuredEventScreen = {},
                    onNavigateToClinicsList = {
                        navigation.bringToFront(Configuration.ClinicsMapScreen)
                    }
                )
            )

            Configuration.ClinicsMapScreen -> Child.ClinicsMapScreen(
                clinicsMapComponentFactory(
                    componentContext = context,
                    mainContext = Dispatchers.Main,
                    ioContext = Dispatchers.IO,
                    onNavigateToClinicsList = {
                        navigation.bringToFront(Configuration.ClinicsListScreen)
                    },
                    onNavigateToClinicDetails = { clinicName, placeId, latLng ->
                        navigation.bringToFront(
                            Configuration.ClinicDetailsScreen(
                                placeInfo = PlaceInfo(
                                    clinicName = clinicName,
                                    placeId = placeId,
                                    latLng = latLng
                                )
                            )
                        )
                    }
                )
            )

            Configuration.ClinicsListScreen -> Child.ClinicsListScreen(
                clinicsListComponentFactory(
                    componentContext = context,
                    mainContext = Dispatchers.Main,
                    ioContext = Dispatchers.IO,
                    onNavigateToClinicDetails = { clinicName, placeId, latLng ->
                        navigation.bringToFront(
                            Configuration.ClinicDetailsScreen(
                                placeInfo = PlaceInfo(
                                    clinicName = clinicName,
                                    placeId = placeId,
                                    latLng = latLng
                                )
                            )
                        )
                    }
                )
            )

            is Configuration.ClinicDetailsScreen -> Child.ClinicDetailsScreen(
                clinicDetailsComponentFactory(
                    componentContext = context,
                    mainContext = Dispatchers.Main,
                    ioContext = Dispatchers.IO,
                    placeInfo = config.placeInfo,
                    onNavigateToFullscreenMap = { latLng ->
                        navigation.bringToFront(
                            Configuration.MapScreen(
                                latLng = latLng
                            )
                        )
                    }
                )
            )

            is Configuration.MapScreen -> Child.MapScreen(
                mapScreenComponent(
                    componentContext = context,
                    mainContext = Dispatchers.Main,
                    ioContext = Dispatchers.IO,
                    latLng = config.latLng,
                    onGoBack = {
                        navigation.pop()
                    }
                )
            )

            Configuration.ProfileScreen -> Child.ProfileScreen(
                profileScreenComponent(
                    componentContext = context,
                    mainContext = Dispatchers.Main,
                    ioContext = Dispatchers.IO,
                    onLogout = {
                        navigation.replaceAll(Configuration.LoginScreen)
                    },
                    onNavigateToPoliciesScreen = {
                        navigation.bringToFront(Configuration.PoliciesScreen)
                    },
                    onNavigateToPrivacyPolicyScreen = {
                        navigation.bringToFront(Configuration.PrivacyPolicyScreen)
                    },
                    onNavigateToContactsScreen = {
                        navigation.bringToFront(Configuration.ContactsScreen)
                    },
                    onNavigateToTermsAndConditionsScreen = {
                        navigation.bringToFront(Configuration.TermsAndConditionsScreen)
                    },
                )
            )

            Configuration.ContactsScreen -> Child.ContactsScreen(
                contactsScreenComponent(
                    componentContext = context,
                    mainContext = Dispatchers.Main,
                    ioContext = Dispatchers.IO,
                )
            )

            Configuration.PrivacyPolicyScreen -> Child.PrivacyPolicyScreen(
                privacyPolicyScreenComponent(
                    componentContext = context,
                    mainContext = Dispatchers.Main,
                    ioContext = Dispatchers.IO,
                )
            )

            Configuration.TermsAndConditionsScreen -> Child.TermsAndConditionsScreen(
                termsAndConditionsScreenComponent(
                    componentContext = context,
                    mainContext = Dispatchers.Main,
                    ioContext = Dispatchers.IO,
                )
            )

            Configuration.PoliciesScreen -> Child.PoliciesScreen(
                policiesScreenComponent(
                    componentContext = context,
                    mainContext = Dispatchers.Main,
                    ioContext = Dispatchers.IO,
                    onNavigateToContactsScreen = {
                        navigation.bringToFront(Configuration.ContactsScreen)
                    }
                )
            )
        }
    }

    @Serializable
    sealed class Configuration {
        @Serializable
        data object LoginScreen : Configuration()

        @Serializable
        data object MainScreen : Configuration()

        @Serializable
        data object ClinicsMapScreen : Configuration()

        @Serializable
        data object ClinicsListScreen : Configuration()

        @Serializable
        data class ClinicDetailsScreen(
            val placeInfo: PlaceInfo
        ) : Configuration()

        @Serializable
        data class MapScreen(
            val latLng: LatLng
        ) : Configuration()


        @Serializable
        data object ProfileScreen : Configuration()

        @Serializable
        data object ContactsScreen : Configuration()

        @Serializable
        data object PrivacyPolicyScreen : Configuration()

        @Serializable
        data object TermsAndConditionsScreen : Configuration()

        @Serializable
        data object PoliciesScreen : Configuration()
    }
}