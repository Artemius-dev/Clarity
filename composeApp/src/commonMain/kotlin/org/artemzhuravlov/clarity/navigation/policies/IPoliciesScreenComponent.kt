package org.artemzhuravlov.clarity.navigation.policies

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import kotlin.coroutines.CoroutineContext

interface IPoliciesScreenComponent {
    val uiState: Value<PoliciesState>

    fun handleEvent(event: PoliciesScreenEvent)

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            mainContext: CoroutineContext,
            ioContext: CoroutineContext,
            onNavigateToContactsScreen: () -> Unit
        ): IPoliciesScreenComponent
    }
}