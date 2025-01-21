package org.artemzhuravlov.clarity.data.repository.config

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore

class FirebaseConfigRepository : IConfigRepository {

    private val firestore = Firebase.firestore

    override suspend fun getPrivacyPolicy(onSuccess: ((String) -> Unit)?,
                                          onError: ((Exception) -> Unit)?) {
        try {
            val document = firestore.collection("CONFIG").document("privacy_policy").get()
            onSuccess?.invoke(document.get<String>("content"))
        } catch (e: Exception) {
            onError?.invoke(e)
        }
    }

    override suspend fun getTermsAndConditions(onSuccess: ((String) -> Unit)?,
                                          onError: ((Exception) -> Unit)?) {
        try {
            val document = firestore.collection("CONFIG").document("terms_and_conditions").get()
            onSuccess?.invoke(document.get<String>("content"))
        } catch (e: Exception) {
            onError?.invoke(e)
        }
    }
}
