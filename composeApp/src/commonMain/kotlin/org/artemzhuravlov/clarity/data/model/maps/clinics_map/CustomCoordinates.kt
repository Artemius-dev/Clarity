package org.artemzhuravlov.clarity.data.model.maps.clinics_map

import dev.jordond.compass.Coordinates
import org.artemzhuravlov.clarity.data.model.place.LatLng

data class CustomCoordinates(
    val coordinates: Coordinates? = null
)

fun CustomCoordinates.toLatLng(): LatLng {
    return LatLng(this.coordinates?.latitude.toString(), this.coordinates?.longitude.toString())
}
