package org.artemzhuravlov.clarity.data.repository.login

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.auth
import org.artemzhuravlov.clarity.data.model.user.AuthenticatedUser
import org.artemzhuravlov.clarity.data.model.user.toFirebaseAuthenticatedUser

class LoginRepository(private val auth: FirebaseAuth) : ILoginRepository {
    override suspend fun login(
        email: String,
        password: String,
        onSuccess: ((AuthenticatedUser?) -> Unit)?,
        onError: ((Exception) -> Unit)?
    ) {
        try {
            val signInResult = auth.signInWithEmailAndPassword(email, password)
            val firebaseUser = signInResult.user
            onSuccess?.invoke(firebaseUser?.toFirebaseAuthenticatedUser())
        } catch (signInException: Exception) {
            onError?.invoke(signInException)
        }
    }

    override suspend fun logout(
        onSuccess: ((AuthenticatedUser?) -> Unit)?,
        onError: ((Exception?) -> Unit)?
    ) {
        try {
            auth.signOut()
            val firebaseUser = auth.currentUser
            onSuccess?.invoke(firebaseUser?.toFirebaseAuthenticatedUser())
        } catch (signOutException: Exception) {
            onError?.invoke(signOutException)
        }
    }

    override suspend fun checkIsUserLoggedIn(isLoggedIn: (() -> Unit)?) {
        val auth = Firebase.auth
        if (auth.currentUser != null) {
            isLoggedIn?.invoke()
        }
    }
}
