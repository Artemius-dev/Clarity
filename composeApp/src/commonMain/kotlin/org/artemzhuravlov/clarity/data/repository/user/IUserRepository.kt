package org.artemzhuravlov.clarity.data.repository.user

import org.artemzhuravlov.clarity.data.model.user.AuthenticatedUser

interface IUserRepository {
    suspend fun getUser(onSuccess: ((AuthenticatedUser?) -> Unit)?,
                        onError: ((Exception) -> Unit)?)
    suspend fun updateUser(user: AuthenticatedUser, onSuccess: (() -> Unit)?,
                           onError: ((Exception) -> Unit)?)
}
