package org.artemzhuravlov.clarity.navigation.policies

import org.artemzhuravlov.clarity.data.model.policies.Policy

data class PoliciesState(
    val policies: List<Policy> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
    val documentUri: String? = null
)
