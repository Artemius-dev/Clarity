package org.artemzhuravlov.clarity.navigation.terms_and_conditions

sealed interface TermsAndConditionsScreenEvent {
    data object LoadTermsAndConditions : TermsAndConditionsScreenEvent
    data object DismissErrorDialog : TermsAndConditionsScreenEvent
}
