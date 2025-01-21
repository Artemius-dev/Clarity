package org.artemzhuravlov.clarity.data.model.place

import kotlinx.serialization.Serializable

@Serializable
data class PlacesResponse(val results: List<PlaceResult>)