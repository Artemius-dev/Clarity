package org.artemzhuravlov.clarity.data.model.place

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LatLng(
    @SerialName("lat")
    val latitude: String = "",
    @SerialName("lng")
    val longitude: String = ""
)
