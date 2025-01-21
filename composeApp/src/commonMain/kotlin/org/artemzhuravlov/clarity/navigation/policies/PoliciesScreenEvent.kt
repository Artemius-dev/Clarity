package org.artemzhuravlov.clarity.navigation.policies

sealed interface PoliciesScreenEvent {
    data object LoadPolicies : PoliciesScreenEvent
    data object NavigateToContacts : PoliciesScreenEvent
    data object DismissErrorDialog : PoliciesScreenEvent
    data object DismissDocumentUri : PoliciesScreenEvent
    data class GetDocumentForPolicy(val policyId: String) : PoliciesScreenEvent
}
