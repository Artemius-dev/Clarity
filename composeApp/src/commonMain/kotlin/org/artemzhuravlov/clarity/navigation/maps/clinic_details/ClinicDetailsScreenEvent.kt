package org.artemzhuravlov.clarity.navigation.maps.clinic_details

sealed interface ClinicDetailsScreenEvent {
    data object OnLoading : ClinicDetailsScreenEvent
    data object NavigateToMap : ClinicDetailsScreenEvent
}