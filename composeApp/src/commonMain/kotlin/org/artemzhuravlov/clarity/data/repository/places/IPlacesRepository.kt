package org.artemzhuravlov.clarity.data.repository.places

import org.artemzhuravlov.clarity.data.model.place.Place
import org.artemzhuravlov.clarity.data.model.place.PlaceDetails

interface IPlacesRepository {
    suspend fun fetchPlaces(
        location: String, radius: Int, type: String,
        onSuccess: ((List<Place>) -> Unit)? = null,
        onError: ((Exception) -> Unit)? = null
    )

    suspend fun fetchPlaceDetails(
        placeId: String,
        onSuccess: ((PlaceDetails) -> Unit)? = null,
        onError: ((Exception) -> Unit)? = null
    )
}