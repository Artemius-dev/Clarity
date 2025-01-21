package org.artemzhuravlov.clarity.navigation.maps.clincs_list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import dev.jordond.compass.Priority
import dev.jordond.compass.geolocation.Geolocator
import dev.jordond.compass.geolocation.GeolocatorResult
import dev.jordond.compass.geolocation.LocationRequest
import dev.jordond.compass.geolocation.TrackingStatus
import dev.jordond.compass.permissions.PermissionState
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.artemzhuravlov.clarity.data.repository.places.IPlacesRepository
import org.artemzhuravlov.clarity.data.model.place.LatLng
import org.artemzhuravlov.clarity.data.model.place.Place
import org.artemzhuravlov.clarity.flow.CommonStateFlow
import org.artemzhuravlov.clarity.flow.common
import org.artemzhuravlov.clarity.data.model.maps.clinics_map.CustomCoordinates
import org.artemzhuravlov.clarity.navigation.maps.clinics_map.IClinicsMapScreenComponent
import kotlin.coroutines.CoroutineContext

class DefaultClinicsListScreenComponent(
    componentContext: ComponentContext,
    mainContext: CoroutineContext,
    private val ioContext: CoroutineContext,
    private val onNavigateToClinicDetails: (String, String, LatLng) -> Unit,
    private val placesRepository: IPlacesRepository,
) : ComponentContext by componentContext, IClinicsListScreenComponent {

    private val scope = coroutineScope(mainContext + SupervisorJob())

    private val _places = MutableStateFlow<List<Place>>(emptyList())
    override val places: CommonStateFlow<List<Place>> = _places.common()

    private val _isLoading = MutableStateFlow(false)
    override val isLoading: CommonStateFlow<Boolean> = _isLoading.common()

    private val _trackingStatus = MutableStateFlow<TrackingStatus>(TrackingStatus.Idle)

    init {
        scope.launch {
            withContext(ioContext) {
                _isLoading.value = true
                loadPlaces(location = "50.422647, 30.384923", radius = 15000, type = "hospital")
            }
        }
        scope.launch {
            places.collectLatest {
                if (places.value.isNotEmpty()) {
                    _isLoading.value = false
                }
            }
        }
    }

    private fun loadPlaces(location: String, radius: Int, type: String) {
        println("Tries to load loadPlaces")
        if (_places.value == emptyList<List<Place>>()) {
            scope.launch {
                withContext(ioContext) {
                    placesRepository.fetchPlaces(
                        location = location,
                        radius = radius,
                        type = type,
                        onSuccess = { places ->
                            _places.value = places
                        },
                    ) {

                    }
                }
            }
        }
    }

    override fun onEvent(event: ClinicsListScreenEvent) {
        when (event) {
            is ClinicsListScreenEvent.NavigateToClinicDetails -> {
                onNavigateToClinicDetails(event.clinicName, event.placeId, event.latLng)
            }
        }
    }

    class Factory(
        private val placesRepository: IPlacesRepository
    ) : IClinicsListScreenComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext,
            mainContext: CoroutineContext,
            ioContext: CoroutineContext,
            onNavigateToClinicDetails: (String, String, LatLng) -> Unit
        ): IClinicsListScreenComponent = DefaultClinicsListScreenComponent(
            componentContext = componentContext,
            mainContext = mainContext,
            ioContext = ioContext,
            onNavigateToClinicDetails = onNavigateToClinicDetails,
            placesRepository = placesRepository
        )
    }
}