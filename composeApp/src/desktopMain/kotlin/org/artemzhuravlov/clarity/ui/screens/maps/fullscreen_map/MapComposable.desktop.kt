package org.artemzhuravlov.clarity.ui.screens.maps.fullscreen_map

import androidx.compose.runtime.Composable
import org.artemzhuravlov.clarity.data.model.place.LatLng

@Composable
actual fun MapComposable(latLng: LatLng) {
}

@Composable
actual fun MapListComposable(
    isLocationPermissionGranted: Boolean,
    nameList: List<String>,
    latLngList: List<LatLng>
) {
}