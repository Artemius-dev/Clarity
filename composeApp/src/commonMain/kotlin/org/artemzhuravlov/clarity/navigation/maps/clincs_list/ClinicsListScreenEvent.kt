package org.artemzhuravlov.clarity.navigation.maps.clincs_list

import org.artemzhuravlov.clarity.data.model.place.LatLng

sealed interface ClinicsListScreenEvent {
    data class NavigateToClinicDetails(
        val clinicName: String,
        val placeId: String,
        val latLng: LatLng
    ) : ClinicsListScreenEvent
}