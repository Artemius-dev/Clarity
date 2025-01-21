package org.artemzhuravlov.clarity.navigation.maps.fullscreen_map

import com.arkivanov.decompose.ComponentContext
import org.artemzhuravlov.clarity.data.model.place.LatLng
import kotlin.coroutines.CoroutineContext

interface IMapScreenComponent {
    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            mainContext: CoroutineContext,
            ioContext: CoroutineContext,
            latLng: LatLng,
            onGoBack: () -> Unit
        ): IMapScreenComponent
    }

    fun getLatLng(): LatLng
    fun goBack()
}