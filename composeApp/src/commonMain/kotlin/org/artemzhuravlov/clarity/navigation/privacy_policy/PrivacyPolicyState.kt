package org.artemzhuravlov.clarity.navigation.privacy_policy

data class PrivacyPolicyState(
    val isLoading: Boolean = true,
    val content: String = "",
    val error: String? = null
)
