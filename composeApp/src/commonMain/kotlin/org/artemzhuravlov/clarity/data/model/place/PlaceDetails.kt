package org.artemzhuravlov.clarity.data.model.place

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlaceDetails(
    val website: String = "",
    @SerialName("formatted_phone_number")
    val phoneNumber: String = "",
    @SerialName("opening_hours")
    val openingHours: OpeningHours? = null,
    val placeInfo: PlaceInfo? = null
)