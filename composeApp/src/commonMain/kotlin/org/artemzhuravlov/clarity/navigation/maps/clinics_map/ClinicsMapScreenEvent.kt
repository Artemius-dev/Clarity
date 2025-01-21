package org.artemzhuravlov.clarity.navigation.maps.clinics_map

import org.artemzhuravlov.clarity.data.model.place.LatLng

sealed interface ClinicsMapScreenEvent {
    data object ProvideOrRequestLocationPermission : ClinicsMapScreenEvent
    data object OnStartLocationTracking: ClinicsMapScreenEvent
    data object OnStopLocationTracking : ClinicsMapScreenEvent
    data object OnMyLocationButtonClick : ClinicsMapScreenEvent
    data object NavigateToClinicsList : ClinicsMapScreenEvent
    data class NavigateToClinicDetails(
        val clinicName: String,
        val placeId: String,
        val latLng: LatLng
    ) : ClinicsMapScreenEvent
}