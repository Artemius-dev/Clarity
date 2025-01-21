package org.artemzhuravlov.clarity.ui.screens.maps.clinics_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
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
import org.artemzhuravlov.clarity.data.model.place.Place
import org.artemzhuravlov.clarity.navigation.maps.clincs_list.ClinicsListScreenEvent
import org.artemzhuravlov.clarity.navigation.maps.clincs_list.IClinicsListScreenComponent
import org.artemzhuravlov.clarity.navigation.maps.clinics_map.IClinicsMapScreenComponent

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ClinicsListScreen(modifier: Modifier = Modifier, component: IClinicsListScreenComponent) {
    val places by component.places.collectAsState()
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
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(places) { place ->
                    PlaceItem(place = place) { placeId ->
                        component.onEvent(
                            ClinicsListScreenEvent.NavigateToClinicDetails(
                                clinicName = place.name,
                                placeId = placeId,
                                latLng = place.latLng
                            )
                        )
                    }
                }
            }
        }
    }
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
