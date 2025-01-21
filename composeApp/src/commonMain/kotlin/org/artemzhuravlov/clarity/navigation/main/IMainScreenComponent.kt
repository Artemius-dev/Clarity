package org.artemzhuravlov.clarity.navigation.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import kotlin.coroutines.CoroutineContext

interface IMainScreenComponent {
    val uiState: Value<MainScreenState>

    fun handleEvent(event: MainScreenEvent)

    fun interface Factory {
        operator fun invoke( // 2
            componentContext: ComponentContext,
            mainContext: CoroutineContext,
            ioContext: CoroutineContext,
            onNavigateToContactsScreen: () -> Unit,
            onNavigateToInsuredEventScreen: () -> Unit,
            onNavigateToClinicsList: () -> Unit,
        ): IMainScreenComponent
    }
}