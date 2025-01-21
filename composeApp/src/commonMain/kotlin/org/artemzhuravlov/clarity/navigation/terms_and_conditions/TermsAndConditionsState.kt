package org.artemzhuravlov.clarity.navigation.terms_and_conditions

data class TermsAndConditionsState(
    val isLoading: Boolean = true,
    val content: String = "",
    val error: String? = null
)
