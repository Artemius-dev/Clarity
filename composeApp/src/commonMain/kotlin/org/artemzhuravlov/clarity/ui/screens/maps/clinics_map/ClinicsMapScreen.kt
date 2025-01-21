package org.artemzhuravlov.clarity.ui.screens.maps.clinics_map

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.platform.annotation.SuppressLint
import dev.jordond.compass.permissions.PermissionState
import org.artemzhuravlov.clarity.data.model.place.Place
import org.artemzhuravlov.clarity.navigation.maps.clinics_map.ClinicsMapScreenEvent
import org.artemzhuravlov.clarity.navigation.maps.clinics_map.IClinicsMapScreenComponent
import org.artemzhuravlov.clarity.resources.Res
import org.artemzhuravlov.clarity.resources.locator
import org.artemzhuravlov.clarity.ui.screens.maps.fullscreen_map.MapListComposable
import org.jetbrains.compose.resources.painterResource

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ClinicsMapScreen(modifier: Modifier = Modifier, component: IClinicsMapScreenComponent) {
    val places by component.places.collectAsState()
    val locationPermission by component.locationPermission.collectAsState()
    val isLoading by component.isLoading.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 60.dp)
    ) {
        if (isLoading && places.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (places.isNotEmpty()) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        FloatingActionButton(
                            onClick = {
                                component.onEvent(ClinicsMapScreenEvent.NavigateToClinicsList)
                            },
                            shape = CircleShape,
                            containerColor = Color.White,
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Список лікарень"
                            )
                        }
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            if (locationPermission == PermissionState.Granted) {
                                component.onEvent(ClinicsMapScreenEvent.OnStartLocationTracking)
                                component.onEvent(ClinicsMapScreenEvent.OnMyLocationButtonClick)
                            } else {
                                component.onEvent(ClinicsMapScreenEvent.ProvideOrRequestLocationPermission)
                            }
                        },
                        shape = CircleShape,
                        containerColor = Color.White,
                        contentColor = when (locationPermission) {
                            PermissionState.Granted -> {
                                Color(0xFF1C73E8)
                            }

                            else -> {
                                Color.Gray
                            }
                        }
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(resource = Res.drawable.locator),
                            contentDescription = "Моє місцезнаходження"
                        )
                    }
                }
            ) {
                MapListComposable(
                    component = component
                )
            }

        }
    }
//    LazyColumn(
//        modifier = Modifier.fillMaxSize().padding(bottom = 60.dp),
//        contentPadding = PaddingValues(vertical = 8.dp)
//    ) {
//        items(places) { place ->
//            PlaceItem(place = place) { placeId ->
//                component.onEvent(
//                    ClinicsListScreenEvent.NavigateToClinicDetails(
//                        clinicName = place.name,
//                        placeId = placeId,
//                        latLng = place.latLng
//                    )
//                )
//            }
//        }
//    }
//    Column(modifier = Modifier.fillMaxSize()) {
//        MapComposable()
//    }
}

@Composable
fun PlaceItem(place: Place, onClickClinic: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClickClinic(place.placeId)
            }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = place.name, fontWeight = FontWeight.Bold)
            Text(text = place.address)
        }
    }
    HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
}
