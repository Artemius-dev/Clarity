package org.artemzhuravlov.clarity.ui.screens.maps.fullscreen_map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitViewController
import org.artemzhuravlov.clarity.data.model.place.LatLng
import org.artemzhuravlov.clarity.mapListViewController
import org.artemzhuravlov.clarity.mapViewController
import org.artemzhuravlov.clarity.navigation.maps.clinics_map.IClinicsMapScreenComponent

@Composable
actual fun MapComposable(latLng: LatLng) {
    UIKitViewController(
        factory = { mapViewController(latLng) },
        modifier = Modifier.fillMaxSize(),
    )
}

@Composable
actual fun MapListComposable(
    component: IClinicsMapScreenComponent
) {
    UIKitViewController(
        factory = {
            mapListViewController(
                component
            )
        },
        modifier = Modifier.fillMaxSize(),
    )
}