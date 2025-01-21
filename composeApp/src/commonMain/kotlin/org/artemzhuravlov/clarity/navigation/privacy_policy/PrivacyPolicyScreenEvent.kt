package org.artemzhuravlov.clarity.navigation.privacy_policy

sealed interface PrivacyPolicyScreenEvent {
    data object LoadPolicy : PrivacyPolicyScreenEvent
    data object DismissErrorDialog : PrivacyPolicyScreenEvent
}
