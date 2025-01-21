package org.artemzhuravlov.clarity.navigation.maps.clinic_details

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import org.artemzhuravlov.clarity.data.model.place.LatLng
import org.artemzhuravlov.clarity.data.model.place.PlaceDetails
import org.artemzhuravlov.clarity.data.model.place.PlaceInfo
import kotlin.coroutines.CoroutineContext

interface IClinicDetailsScreenComponent {
    val placeDetails: Value<PlaceDetails>

    fun fetchPlaceDetails(placeId: String)

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            mainContext: CoroutineContext,
            ioContext: CoroutineContext,
            placeInfo: PlaceInfo,
            onNavigateToFullscreenMap: (LatLng) -> Unit,
        ): IClinicDetailsScreenComponent
    }

    fun onEvent(event: ClinicDetailsScreenEvent)
}