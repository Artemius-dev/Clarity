package org.artemzhuravlov.clarity.navigation.maps.clinics_map

import com.arkivanov.decompose.ComponentContext
import dev.jordond.compass.permissions.PermissionState
import org.artemzhuravlov.clarity.data.model.place.LatLng
import org.artemzhuravlov.clarity.data.model.place.Place
import org.artemzhuravlov.clarity.flow.CommonStateFlow
import org.artemzhuravlov.clarity.data.model.maps.clinics_map.CustomCoordinates
import kotlin.coroutines.CoroutineContext

interface IClinicsMapScreenComponent {
    val places: CommonStateFlow<List<Place>>
    val locationPermission: CommonStateFlow<PermissionState>
    val locationCoordinates: CommonStateFlow<CustomCoordinates>
    val isMyLocationButtonClicked: CommonStateFlow<Boolean>
    val isLoading: CommonStateFlow<Boolean>

    fun setMyLocationButtonClicked()

    fun onEvent(event: ClinicsMapScreenEvent)

    fun interface Factory {
        operator fun invoke( // 2
            componentContext: ComponentContext,
            mainContext: CoroutineContext,
            ioContext: CoroutineContext,
            onNavigateToClinicsList: () -> Unit,
            onNavigateToClinicDetails: (String, String, LatLng) -> Unit
        ): IClinicsMapScreenComponent
    }
}