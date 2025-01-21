package org.artemzhuravlov.clarity.data.repository.main

import org.artemzhuravlov.clarity.data.model.main.Notification
import org.artemzhuravlov.clarity.data.model.main.Offer
import org.artemzhuravlov.clarity.data.model.place.Place
import org.artemzhuravlov.clarity.data.model.place.PlaceDetails

interface IUserDataRepository {
    suspend fun fetchOffers(
        onSuccess: ((List<Offer>) -> Unit)? = null,
        onError: ((Exception) -> Unit)? = null
    )

    suspend fun fetchNotifications(
        onSuccess: ((List<Notification>) -> Unit)? = null,
        onError: ((Exception) -> Unit)? = null
    )
}