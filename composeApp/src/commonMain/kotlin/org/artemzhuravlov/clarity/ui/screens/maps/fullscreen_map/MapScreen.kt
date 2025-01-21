package org.artemzhuravlov.clarity.ui.screens.maps.fullscreen_map

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.artemzhuravlov.clarity.navigation.maps.fullscreen_map.IMapScreenComponent
import org.artemzhuravlov.clarity.resources.Res
import org.artemzhuravlov.clarity.resources.ic_close_map
import org.jetbrains.compose.resources.painterResource

@Composable
fun MapScreen(modifier: Modifier = Modifier, component: IMapScreenComponent) {
    Box(modifier = Modifier.fillMaxSize().padding(bottom = 60.dp)) {
        MapComposable(component.getLatLng())
        FloatingActionButton(
            modifier = Modifier.padding(16.dp).align(Alignment.BottomEnd),
            containerColor = Color.White,
            onClick = {
                component.goBack()
            }) {
            Image(
                painter = painterResource(Res.drawable.ic_close_map),
                contentDescription = "Закрити мапу"
            )
        }
    }
}