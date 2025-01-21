package org.artemzhuravlov.clarity.navigation.maps.clinics_map

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
import kotlin.coroutines.CoroutineContext

class DefaultClinicsMapScreenComponent(
    componentContext: ComponentContext,
    mainContext: CoroutineContext,
    private val ioContext: CoroutineContext,
    private val onNavigateToClinicsList: () -> Unit,
    private val onNavigateToClinicDetails: (String, String, LatLng) -> Unit,
    private val placesRepository: IPlacesRepository,
    private val geoLocation: Geolocator
) : ComponentContext by componentContext, IClinicsMapScreenComponent {

    private val scope = coroutineScope(mainContext + SupervisorJob())

    private val _places = MutableStateFlow<List<Place>>(emptyList())
    override val places: CommonStateFlow<List<Place>> = _places.common()

    private val _locationPermission = MutableStateFlow<PermissionState>(PermissionState.NotDetermined)
    override val locationPermission: CommonStateFlow<PermissionState> = _locationPermission.common()

    private val _locationCoordinates = MutableStateFlow<CustomCoordinates>(CustomCoordinates())
    override val locationCoordinates: CommonStateFlow<CustomCoordinates> = _locationCoordinates.common()

    private val _isMyLocationButtonClicked = MutableStateFlow(false)
    override val isMyLocationButtonClicked: CommonStateFlow<Boolean> = _isMyLocationButtonClicked.common()

    private val _isLoading = MutableStateFlow(false)
    override val isLoading: CommonStateFlow<Boolean> = _isLoading.common()

    private val _trackingStatus = MutableStateFlow<TrackingStatus>(TrackingStatus.Idle)

    init {
        scope.launch {
            withContext(ioContext) {
                loadPlaces(location = "50.422647, 30.384923", radius =  5000, type = "hospital")
            }
        }
        scope.launch {
            geoLocation.trackingStatus.collectLatest {
                _trackingStatus.value = it
            }
        }
        scope.launch {
            geoLocation.locationUpdates.collectLatest {
                _locationCoordinates.value = CustomCoordinates(coordinates = it.coordinates)
            }
        }
    }

    private fun loadPlaces(location: String, radius: Int, type: String) {
        println("Tries to load loadPlaces")
        if (_places.value == emptyList<List<Place>>()) {
            _isLoading.value = true
            scope.launch {
                withContext(ioContext) {
                    placesRepository.fetchPlaces(
                        location = location,
                        radius = radius,
                        type = type,
                        onSuccess = { places ->
                            _isLoading.value = false
                            _places.value = places
                        },
                    ) {
                        _isLoading.value = false
                    }
                }
            }
        }
    }

    override fun setMyLocationButtonClicked() {
        scope.launch {
            _isMyLocationButtonClicked.value = _isMyLocationButtonClicked.value.not()
        }
    }

    override fun onEvent(event: ClinicsMapScreenEvent) {
        when (event) {
            is ClinicsMapScreenEvent.NavigateToClinicDetails -> {
                onNavigateToClinicDetails(event.clinicName, event.placeId, event.latLng)
            }

            ClinicsMapScreenEvent.ProvideOrRequestLocationPermission -> {
                provideOrRequestLocationPermission()
            }

            ClinicsMapScreenEvent.OnStartLocationTracking -> {
                startLocationTracking()
            }
            ClinicsMapScreenEvent.OnStopLocationTracking -> {
                stopLocationTracking()
            }

            ClinicsMapScreenEvent.OnMyLocationButtonClick -> {
                scope.launch {
                    _isMyLocationButtonClicked.value = _isMyLocationButtonClicked.value.not()
                }
            }

            ClinicsMapScreenEvent.NavigateToClinicsList -> {
                onNavigateToClinicsList()
            }
        }
    }

    private fun provideOrRequestLocationPermission() {
        scope.launch {
            when (val result = geoLocation.current(Priority.HighAccuracy)) {
                is GeolocatorResult.Success -> {
                    _locationPermission.value = PermissionState.Granted
                    _locationCoordinates.value = CustomCoordinates(coordinates = result.data.coordinates)
                    println("LOCATION: ${locationCoordinates.value.coordinates}")
                }

                is GeolocatorResult.Error -> when (result) {
                    is GeolocatorResult.NotSupported -> println("LOCATION ERROR: ${result.message}")
                    is GeolocatorResult.NotFound -> println("LOCATION ERROR: ${result.message}")
                    is GeolocatorResult.PermissionError -> println("LOCATION ERROR: ${result.message}")
                    is GeolocatorResult.PermissionDenied -> {
                        _locationPermission.value = PermissionState.Denied
                    }
                    is GeolocatorResult.GeolocationFailed -> println("LOCATION ERROR: ${result.message}")
                    else -> println("LOCATION ERROR: ${result.message}")
                }
            }
        }
    }

    private fun startLocationTracking() {
        scope.launch {
            if (_trackingStatus.value == TrackingStatus.Idle) {
                geoLocation.startTracking(
                    LocationRequest(Priority.HighAccuracy)
                )
            }
        }
    }

    private fun stopLocationTracking() {
        scope.launch {
            if (_trackingStatus.value != TrackingStatus.Idle) {
                geoLocation.startTracking(
                    LocationRequest(Priority.HighAccuracy)
                )
            }
        }
    }

    class Factory(
        private val placesRepository: IPlacesRepository,
        private val geoLocation: Geolocator
    ) : IClinicsMapScreenComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext,
            mainContext: CoroutineContext,
            ioContext: CoroutineContext,
            onNavigateToClinicsList: () -> Unit,
            onNavigateToClinicDetails: (String, String, LatLng) -> Unit
        ): IClinicsMapScreenComponent = DefaultClinicsMapScreenComponent(
            componentContext = componentContext,
            mainContext = mainContext,
            ioContext = ioContext,
            onNavigateToClinicsList = onNavigateToClinicsList,
            onNavigateToClinicDetails = onNavigateToClinicDetails,
            placesRepository = placesRepository,
            geoLocation = geoLocation
        )
    }
}