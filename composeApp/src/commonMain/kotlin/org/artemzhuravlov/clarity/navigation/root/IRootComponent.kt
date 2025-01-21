package org.artemzhuravlov.clarity.navigation.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import org.artemzhuravlov.clarity.navigation.contacts.IContactsScreenComponent
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

interface IRootComponent {
    val childStack: Value<ChildStack<DefaultRootComponent.Configuration, Child>>

    sealed class Child {
        data class LoginScreen(val component: ILoginScreenComponent): Child()
        data class MainScreen(val component: IMainScreenComponent): Child()
        data class ClinicsMapScreen(val component: IClinicsMapScreenComponent): Child()
        data class ClinicsListScreen(val component: IClinicsListScreenComponent): Child()
        data class ClinicDetailsScreen(val component: IClinicDetailsScreenComponent): Child()
        data class MapScreen(val component: IMapScreenComponent): Child()
        data class ProfileScreen(val component: IProfileScreenComponent): Child()
        data class ContactsScreen(val component: IContactsScreenComponent): Child()
        data class PrivacyPolicyScreen(val component: IPrivacyPolicyScreenComponent): Child()
        data class TermsAndConditionsScreen(val component: ITermsAndConditionsScreenComponent): Child()
        data class PoliciesScreen(val component: IPoliciesScreenComponent): Child()
    }

    fun navigateToMain()
    fun navigateToClinicsMap()
    fun navigateToProfile()

    fun interface Factory {
        operator fun invoke(componentContext: ComponentContext): DefaultRootComponent
    }
}

