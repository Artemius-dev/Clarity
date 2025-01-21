package org.artemzhuravlov.clarity.navigation.profile

sealed interface ProfileScreenEvent {
    data object LoadProfile : ProfileScreenEvent
    data object DismissErrorDialog : ProfileScreenEvent
    data object OnLogout : ProfileScreenEvent
    data object NavigateToPolicies : ProfileScreenEvent
    data object NavigateToContacts : ProfileScreenEvent
    data object NavigateToPrivacyPolicy : ProfileScreenEvent
    data object NavigateToTermsAndConditions : ProfileScreenEvent
}
