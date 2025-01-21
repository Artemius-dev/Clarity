package org.artemzhuravlov.clarity.data.model.user

interface AuthenticatedUser {
    val uid: String
    val email: String?
    val displayName: String?
}

fun AuthenticatedUser.copy(
    displayName: String? = this.displayName
): AuthenticatedUser {
    return FirebaseAuthenticatedUser(
        uid = this.uid,
        email = this.email,
        displayName = displayName
    )
}
