package org.artemzhuravlov.clarity.ui.screens.maps.fullscreen_map

import androidx.compose.runtime.Composable
import org.artemzhuravlov.clarity.data.model.place.LatLng
import org.artemzhuravlov.clarity.navigation.maps.clinics_map.IClinicsMapScreenComponent

@Composable
expect fun MapComposable(latLng: LatLng)

@Composable
expect fun MapListComposable(
    component: IClinicsMapScreenComponent
)