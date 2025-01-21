package org.artemzhuravlov.clarity.data.model.user

import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.firestore.DocumentSnapshot
import kotlinx.serialization.Serializable

@Serializable
data class FirebaseAuthenticatedUser(
    override val uid: String,
    override val email: String? = null,
    override val displayName: String? = null,
) : AuthenticatedUser

fun FirebaseUser.toFirebaseAuthenticatedUser(): FirebaseAuthenticatedUser {
    return FirebaseAuthenticatedUser(
        uid = this.uid,
        email = this.email,
        displayName = this.displayName
    )
}

fun DocumentSnapshot.toAuthenticatedUser(): FirebaseAuthenticatedUser? {
    return try {
        val uid = id
        val email = get<String>("email")
        val displayName = get<String>("displayName")
        FirebaseAuthenticatedUser(
            uid = uid,
            email = email,
            displayName = displayName,
        )
    } catch (e: Exception) {
        println("Error mapping user: ${e.message}")
        null
    }
}