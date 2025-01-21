package org.artemzhuravlov.clarity.data.repository.user

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.FirebaseFirestoreSettings
import dev.gitlive.firebase.firestore.LocalCacheSettings
import dev.gitlive.firebase.firestore.LocalCacheSettings.Persistent
import dev.gitlive.firebase.firestore.firestore
import dev.gitlive.firebase.firestore.firestoreSettings
import org.artemzhuravlov.clarity.data.model.user.AuthenticatedUser
import org.artemzhuravlov.clarity.data.model.user.toAuthenticatedUser

class FirebaseUserRepository() : IUserRepository {

    override suspend fun getUser(
        onSuccess: ((AuthenticatedUser?) -> Unit)?,
        onError: ((Exception) -> Unit)?
    ) {
        try {
            val auth = Firebase.auth
            val firestore = Firebase.firestore
            val snapshot = firestore.collection("USERS").document(auth.currentUser!!.uid).get()
            onSuccess?.invoke(snapshot.toAuthenticatedUser())
        } catch (e: Exception) {
            onError?.invoke(e)
        }
    }

    override suspend fun updateUser(
        user: AuthenticatedUser,
        onSuccess: (() -> Unit)?,
        onError: ((Exception) -> Unit)?
    ) {
        try {
            val firestore = Firebase.firestore
            firestore.collection("USERS").document(user.uid).set(user, merge = true)
            onSuccess?.invoke()
        } catch (e: Exception) {
            onError?.invoke(e)
        }
    }
}
