package org.artemzhuravlov.clarity.data.model.place

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlaceResult(
    val name: String = "",
    val vicinity: String = "",
    @SerialName("place_id")
    val placeId: String = "",
    val geometry: Geometry? = null
)
