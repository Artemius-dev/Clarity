package org.artemzhuravlov.clarity.data.repository.policies

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import org.artemzhuravlov.clarity.data.model.policies.Policy

class PoliciesRepository : IPoliciesRepository {
    override suspend fun getPolicies(
        onSuccess: ((List<Policy>) -> Unit)?,
        onError: ((Exception) -> Unit)?
    ) {
        val firestore = Firebase.firestore
        val uid = Firebase.auth.currentUser?.uid
        val policies = mutableListOf<Policy>()

        if (uid != null) {
            try {
                val querySnapshot = firestore.collection("USERS")
                    .document(uid)
                    .collection("POLICIES")
                    .get()
                for (document in querySnapshot.documents) {
                    val policy = document.data<Policy>()
                    policies.add(policy)
                }
                onSuccess?.invoke(policies)
            } catch (e: Exception) {
                onError?.invoke(e)
            }
        } else {
            onError?.invoke(Exception("Щось пішло не так, будь ласка спробуйте ще раз пізніше."))
        }
    }
}