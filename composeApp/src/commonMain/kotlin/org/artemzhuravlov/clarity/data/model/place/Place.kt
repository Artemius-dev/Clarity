package org.artemzhuravlov.clarity.data.model.place

data class Place(
    val name: String,
    val address: String,
    val placeId: String,
    val latLng: LatLng
)
