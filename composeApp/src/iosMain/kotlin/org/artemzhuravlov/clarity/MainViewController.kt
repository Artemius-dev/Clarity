package org.artemzhuravlov.clarity

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import org.artemzhuravlov.clarity.data.model.place.LatLng
import org.artemzhuravlov.clarity.di.koin
import org.artemzhuravlov.clarity.navigation.maps.clinics_map.IClinicsMapScreenComponent
import org.artemzhuravlov.clarity.navigation.root.IRootComponent
import platform.UIKit.UIViewController

fun MainViewController(
    mapUIViewController: (LatLng) -> UIViewController,
    mapListUIViewController: (IClinicsMapScreenComponent) -> UIViewController
) = ComposeUIViewController {
    mapViewController = mapUIViewController
    mapListViewController = mapListUIViewController
    val rootComponent = remember {
        val rootComponentFactory: IRootComponent.Factory by koin.inject()
        rootComponentFactory(DefaultComponentContext(LifecycleRegistry()))
    }
    App(rootComponent = rootComponent)
}

lateinit var mapViewController: (LatLng) -> UIViewController

fun MapListViewController(
) = ComposeUIViewController {
    val rootComponent = remember {
        val rootComponentFactory: IRootComponent.Factory by koin.inject()
        rootComponentFactory(DefaultComponentContext(LifecycleRegistry()))
    }
    App(rootComponent = rootComponent)
}

lateinit var mapListViewController: (
    IClinicsMapScreenComponent
) -> UIViewController