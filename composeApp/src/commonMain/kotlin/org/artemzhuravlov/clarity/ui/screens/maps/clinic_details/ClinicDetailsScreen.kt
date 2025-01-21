package org.artemzhuravlov.clarity.ui.screens.maps.clinic_details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import org.artemzhuravlov.clarity.navigation.maps.clinic_details.ClinicDetailsScreenEvent
import org.artemzhuravlov.clarity.navigation.maps.clinic_details.IClinicDetailsScreenComponent
import org.artemzhuravlov.clarity.resources.Res
import org.artemzhuravlov.clarity.resources.ic_call
import org.artemzhuravlov.clarity.resources.ic_fullscreen_map
import org.artemzhuravlov.clarity.resources.ic_pin
import org.artemzhuravlov.clarity.resources.ic_website
import org.artemzhuravlov.clarity.ui.screens.maps.fullscreen_map.MapComposable
import org.artemzhuravlov.clarity.ui.utils.makePhoneCall
import org.artemzhuravlov.clarity.ui.utils.openExternalWebPage
import org.artemzhuravlov.clarity.ui.utils.openGoogleMaps
import org.artemzhuravlov.clarity.ui.utils.openTelegramChat
import org.jetbrains.compose.resources.painterResource

@Composable
fun ClinicDetailsScreen(
    component: IClinicDetailsScreenComponent
) {
    val details = component.placeDetails.subscribeAsState()
    val scrollState = rememberScrollState()

    var makePhoneCallTo by remember {
        mutableStateOf("")
    }

    var isOpenWebsite by remember {
        mutableStateOf("")
    }

    var isOpenMaps by remember {
        mutableStateOf(false)
    }

    if (makePhoneCallTo.isNotBlank()) {
        makePhoneCall(makePhoneCallTo)
        makePhoneCallTo = ""
    }

    if (isOpenWebsite.isNotBlank()) {
        openExternalWebPage(details.value.website)
        isOpenWebsite = ""
    }

    if (isOpenMaps) {
        openGoogleMaps(
            details.value.placeInfo!!.latLng,
            label = details.value.placeInfo?.clinicName
        )
        isOpenMaps = false
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        details.value.placeInfo?.clinicName?.let {
            Text(
                text = it,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        Box(modifier = Modifier.fillMaxWidth().height(200.dp)) {
            if (details.value.placeInfo != null
                && details.value.placeInfo!!.latLng.latitude.isNotBlank()
                && details.value.placeInfo!!.latLng.longitude.isNotBlank()
            ) {
                MapComposable(details.value.placeInfo!!.latLng)

                FloatingActionButton(
                    modifier = Modifier.align(Alignment.BottomEnd),
                    containerColor = Color.White,
                    onClick = {
                        component.onEvent(ClinicDetailsScreenEvent.NavigateToMap)
                    }) {
                    Image(
                        painter = painterResource(Res.drawable.ic_fullscreen_map),
                        contentDescription = "Відкрити мапу у повному розмірі"
                    )
                }
            }

        }

        Text(
            text = "Де нас знайти?",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Text(
            text = "Телефон: ${details.value.phoneNumber.ifBlank { "N/A" }}",
            color = Color.Black,
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = 4.dp)
        )

        Text(
            text = "Вебсайт: ${details.value.website.ifBlank { "N/A" }}",
            color = Color.Black,
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = 4.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            if (details.value.website.isNotBlank()) {
                Button(
                    onClick = {
                        isOpenWebsite = details.value.website
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = painterResource(Res.drawable.ic_website),
                            contentDescription = "Вебсайт"
                        )
                        Text("Вебсайт", color = MaterialTheme.colorScheme.primary)
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
            }

            if (details.value.phoneNumber.isNotBlank()) {
                Button(
                    onClick = {
                        makePhoneCallTo = details.value.phoneNumber
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = painterResource(Res.drawable.ic_call),
                            contentDescription = "Телефон"
                        )
                        Text("Телефон", color = MaterialTheme.colorScheme.primary)
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))
            }

            Button(
                onClick = {
                    isOpenMaps = true
                },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(Res.drawable.ic_pin),
                        contentDescription = "Шлях"
                    )
                    Text("Шлях", color = MaterialTheme.colorScheme.primary)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        details.value.openingHours?.openingHoursToOpeningTime()
            ?.let {
                Text(
                    text = "Час роботи",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                HorizontalDivider(color = Color.Gray, thickness = 1.dp)
                OpeningTimes(openingTime = it)
            }
    }
}
