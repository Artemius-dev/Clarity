package org.artemzhuravlov.clarity.navigation.profile

import org.artemzhuravlov.clarity.data.model.user.AuthenticatedUser

data class ProfileScreenState(
    val isLoading: Boolean = true,
    val user: AuthenticatedUser? = null,
    val error: String? = null
)
