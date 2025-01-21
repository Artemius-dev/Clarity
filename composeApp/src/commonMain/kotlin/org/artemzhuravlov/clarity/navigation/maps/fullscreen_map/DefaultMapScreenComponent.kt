package org.artemzhuravlov.clarity.navigation.maps.fullscreen_map

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import kotlinx.coroutines.SupervisorJob
import org.artemzhuravlov.clarity.data.model.place.LatLng
import kotlin.coroutines.CoroutineContext

class DefaultMapScreenComponent(
    componentContext: ComponentContext,
    mainContext: CoroutineContext,
    private val ioContext: CoroutineContext,
    private val latLng: LatLng,
    private val onGoBack: () -> Unit
): ComponentContext by componentContext, IMapScreenComponent {

    private val scope = coroutineScope(mainContext + SupervisorJob())

    override fun getLatLng(): LatLng {
        return latLng
    }

    override fun goBack() {
        onGoBack()
    }

    class Factory(): IMapScreenComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext,
            mainContext: CoroutineContext,
            ioContext: CoroutineContext,
            latLng: LatLng,
            onGoBack: () -> Unit
        ): IMapScreenComponent = DefaultMapScreenComponent(
            componentContext = componentContext,
            mainContext = mainContext,
            ioContext = ioContext,
            latLng = latLng,
            onGoBack = onGoBack
        )
    }
}