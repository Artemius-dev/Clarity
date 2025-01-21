package org.artemzhuravlov.clarity.data.repository.main

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import org.artemzhuravlov.clarity.data.model.main.Notification
import org.artemzhuravlov.clarity.data.model.main.Offer
import org.artemzhuravlov.clarity.data.model.place.Place
import org.artemzhuravlov.clarity.data.model.policies.Policy

class UserDataRepository() : IUserDataRepository {
    override suspend fun fetchOffers(
        onSuccess: ((List<Offer>) -> Unit)?,
        onError: ((Exception) -> Unit)?
    ) {
        val firestore = Firebase.firestore
        val uid = Firebase.auth.currentUser?.uid
        val offers = mutableListOf<Offer>()

        if (uid != null) {
            try {
                val querySnapshot = firestore.collection("OFFERS")
                    .get()
                for (document in querySnapshot.documents) {
                    val offer = document.data<Offer>()
                    offers.add(offer)
                }
                onSuccess?.invoke(offers)
            } catch (e: Exception) {
                onError?.invoke(e)
            }
        } else {
            onError?.invoke(Exception("Щось пішло не так, будь ласка спробуйте ще раз пізніше."))
        }
    }

    override suspend fun fetchNotifications(
        onSuccess: ((List<Notification>) -> Unit)?,
        onError: ((Exception) -> Unit)?
    ) {
        val firestore = Firebase.firestore
        val uid = Firebase.auth.currentUser?.uid
        val notifications = mutableListOf<Notification>()

        if (uid != null) {
            try {
                val querySnapshot = firestore.collection("USERS")
                    .document(uid)
                    .collection("NOTIFICATIONS")
                    .get()
                for (document in querySnapshot.documents) {
                    val notification = document.data<Notification>()
                    notifications.add(notification)
                }
                onSuccess?.invoke(notifications)
            } catch (e: Exception) {
                onError?.invoke(e)
            }
        } else {
            onError?.invoke(Exception("Щось пішло не так, будь ласка спробуйте ще раз пізніше."))
        }
    }

}
