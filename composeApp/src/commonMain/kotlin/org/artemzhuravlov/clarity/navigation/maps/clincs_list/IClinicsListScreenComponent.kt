package org.artemzhuravlov.clarity.navigation.maps.clincs_list

import com.arkivanov.decompose.ComponentContext
import dev.jordond.compass.permissions.PermissionState
import org.artemzhuravlov.clarity.data.model.place.LatLng
import org.artemzhuravlov.clarity.data.model.place.Place
import org.artemzhuravlov.clarity.flow.CommonStateFlow
import org.artemzhuravlov.clarity.data.model.maps.clinics_map.CustomCoordinates
import kotlin.coroutines.CoroutineContext

interface IClinicsListScreenComponent {
    val places: CommonStateFlow<List<Place>>
    val isLoading: CommonStateFlow<Boolean>

    fun onEvent(event: ClinicsListScreenEvent)

    fun interface Factory {
        operator fun invoke( // 2
            componentContext: ComponentContext,
            mainContext: CoroutineContext,
            ioContext: CoroutineContext,
            onNavigateToClinicDetails: (String, String, LatLng) -> Unit
        ): IClinicsListScreenComponent
    }
}