package org.artemzhuravlov.clarity.data.model.place

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Geometry(
    @SerialName("location")
    val latLng: LatLng? = null
)