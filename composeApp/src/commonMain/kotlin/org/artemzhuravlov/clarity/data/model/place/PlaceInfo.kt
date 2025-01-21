package org.artemzhuravlov.clarity.data.model.place

import kotlinx.serialization.Serializable

@Serializable
data class PlaceInfo(
    val clinicName: String,
    val placeId: String,
    val latLng: LatLng
)
