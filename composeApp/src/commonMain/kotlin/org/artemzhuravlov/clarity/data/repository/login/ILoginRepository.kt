package org.artemzhuravlov.clarity.data.repository.login

import org.artemzhuravlov.clarity.data.model.user.AuthenticatedUser

interface ILoginRepository {
    suspend fun login(
        email: String,
        password: String,
        onSuccess: ((AuthenticatedUser?) -> Unit)? = null,
        onError: ((Exception) -> Unit)? = null
    )

    suspend fun logout(
        onSuccess: ((AuthenticatedUser?) -> Unit)? = null,
        onError: ((Exception?) -> Unit)? = null
    )

    suspend fun checkIsUserLoggedIn(isLoggedIn: (() -> Unit)? = null)
}