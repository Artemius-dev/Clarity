package org.artemzhuravlov.clarity.ui.screens.maps.fullscreen_map

import android.location.Location
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.LocationSource
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.clustering.Clustering
import com.google.maps.android.compose.clustering.rememberClusterManager
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import dev.jordond.compass.Coordinates
import dev.jordond.compass.permissions.PermissionState
import org.artemzhuravlov.clarity.navigation.maps.clinics_map.ClinicsMapScreenEvent
import org.artemzhuravlov.clarity.navigation.maps.clinics_map.IClinicsMapScreenComponent
import org.artemzhuravlov.clarity.data.model.place.LatLng as LatLngOfPlace

@Composable
actual fun MapComposable(latLng: LatLngOfPlace) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        val coordinates = LatLng(latLng.latitude.toDouble(), latLng.longitude.toDouble())
        val markerState = rememberMarkerState(position = coordinates)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(coordinates, 14f)
        }
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(zoomControlsEnabled = false)
        ) {
            Marker(
                state = markerState,
            )
        }
    }
}

@OptIn(MapsComposeExperimentalApi::class)
@Composable
actual fun MapListComposable(
    component: IClinicsMapScreenComponent
) {
    val currentUserLocation by component.locationCoordinates.collectAsStateWithLifecycle()
    val places by component.places.collectAsStateWithLifecycle()
    val locationPermission by component.locationPermission.collectAsStateWithLifecycle()
    val isMyLocationButtonClicked by component.isMyLocationButtonClicked.collectAsStateWithLifecycle()

    var currentUserCoordinates by remember {
        mutableStateOf<Coordinates?>(null)
    }
    val isLocationPermissionGranted = locationPermission == PermissionState.Granted

    val myLocationSource = object : LocationSource {
        var listener: LocationSource.OnLocationChangedListener? = null

        override fun activate(p0: LocationSource.OnLocationChangedListener) {
            this.listener = p0
        }

        override fun deactivate() {
            this.listener = null
        }

        fun onLocation(userLocation: Location) {
            listener?.onLocationChanged(userLocation)
        }
    }

    fun createLocationFromLatLng(latitude: Double, longitude: Double): Location {
        val location = Location("custom") // "custom" is the provider name
        location.latitude = latitude
        location.longitude = longitude
        return location
    }

    val coordinates =
        LatLng(places[0].latLng.latitude.toDouble(), places[0].latLng.longitude.toDouble())

    var cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(coordinates, 14f)
    }

    LaunchedEffect(isMyLocationButtonClicked) {
        if (isMyLocationButtonClicked && currentUserCoordinates != null) {
            cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        currentUserCoordinates!!.latitude,
                        currentUserCoordinates!!.longitude
                    ), 14f
                )
            )
            component.onEvent(ClinicsMapScreenEvent.OnMyLocationButtonClick)
        }
    }

    LaunchedEffect(currentUserLocation) {
        if (currentUserLocation.coordinates != null) {
            currentUserCoordinates = currentUserLocation.coordinates!!
            myLocationSource.onLocation(
                createLocationFromLatLng(
                    currentUserCoordinates!!.latitude,
                    currentUserCoordinates!!.longitude
                )
            )
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        val mapProperties = MapProperties(
            isBuildingEnabled = false,
            isIndoorEnabled = false,
            isMyLocationEnabled = isLocationPermissionGranted,
            isTrafficEnabled = false,
            mapStyleOptions = null,
            mapType = MapType.TERRAIN,
            maxZoomPreference = 21f,
            minZoomPreference = 3f
        )

        val mapUiSettings = MapUiSettings(
            compassEnabled = true,
            indoorLevelPickerEnabled = false,
            mapToolbarEnabled = false,
            myLocationButtonEnabled = false,
            rotationGesturesEnabled = true,
            scrollGesturesEnabled = true,
            scrollGesturesEnabledDuringRotateOrZoom = true,
            tiltGesturesEnabled = true,
            zoomControlsEnabled = false,
            zoomGesturesEnabled = true
        )
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = mapProperties,
            uiSettings = mapUiSettings,
            locationSource = myLocationSource
        ) {
            val clusterManager = rememberClusterManager<ClusterItemImpl>()
            clusterManager?.let { manager ->
                Clustering(
                    items = places.map {
                        ClusterItemImpl(
                            location = LatLng(
                                it.latLng.latitude.toDouble(),
                                it.latLng.longitude.toDouble()
                            ),
                            name = it.name
                        )
                    },
                    clusterManager = manager
                )
            }

        }
    }
}