package org.artemzhuravlov.clarity.navigation.maps.clinic_details

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.artemzhuravlov.clarity.data.repository.places.IPlacesRepository
import org.artemzhuravlov.clarity.data.model.place.LatLng
import org.artemzhuravlov.clarity.data.model.place.PlaceDetails
import org.artemzhuravlov.clarity.data.model.place.PlaceInfo
import kotlin.coroutines.CoroutineContext

class DefaultClinicDetailsScreenComponent(
    componentContext: ComponentContext,
    mainContext: CoroutineContext,
    private val ioContext: CoroutineContext,
    private val placeInfo: PlaceInfo,
    private val onNavigateToFullscreenMap: (LatLng) -> Unit,
    private val placesRepository: IPlacesRepository
): ComponentContext by componentContext, IClinicDetailsScreenComponent {

    private val scope = coroutineScope(mainContext + SupervisorJob())

    private val _placeDetails = MutableValue<PlaceDetails>(PlaceDetails())
    override val placeDetails: Value<PlaceDetails> = _placeDetails

    // TODO: Fix, cause on each Composable invokation it requests data
    init {
        scope.launch {
            fetchPlaceDetails(placeInfo.placeId)
        }
    }

    override fun fetchPlaceDetails(placeId: String) {
        scope.launch {
            withContext(ioContext) {
                placesRepository.fetchPlaceDetails(placeId = placeId, onSuccess = { placeDetails ->
                    _placeDetails.value = placeDetails.copy(placeInfo = placeInfo)
                }) {

                }
            }
        }
    }

    override fun onEvent(event: ClinicDetailsScreenEvent) {
        when (event) {
            is ClinicDetailsScreenEvent.NavigateToMap -> {
                placeDetails.value.placeInfo?.latLng?.let { onNavigateToFullscreenMap(it) }
            }
            ClinicDetailsScreenEvent.OnLoading -> {

            }
        }
    }

    class Factory(
        private val placesRepository: IPlacesRepository
    ): IClinicDetailsScreenComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext,
            mainContext: CoroutineContext,
            ioContext: CoroutineContext,
            placeInfo: PlaceInfo,
            onNavigateToFullscreenMap: (LatLng) -> Unit,
        ): IClinicDetailsScreenComponent = DefaultClinicDetailsScreenComponent(
            componentContext = componentContext,
            mainContext = mainContext,
            ioContext = ioContext,
            placeInfo = placeInfo,
            onNavigateToFullscreenMap = onNavigateToFullscreenMap,
            placesRepository = placesRepository
        )
    }
}