package org.artemzhuravlov.clarity.navigation.terms_and_conditions

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import kotlin.coroutines.CoroutineContext

interface ITermsAndConditionsScreenComponent {
    val uiState: Value<TermsAndConditionsState>
    fun handleEvent(event: TermsAndConditionsScreenEvent)

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            mainContext: CoroutineContext,
            ioContext: CoroutineContext,
        ): ITermsAndConditionsScreenComponent
    }
}