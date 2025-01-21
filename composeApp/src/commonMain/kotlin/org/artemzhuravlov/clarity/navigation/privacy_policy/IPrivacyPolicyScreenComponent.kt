package org.artemzhuravlov.clarity.navigation.privacy_policy

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import kotlin.coroutines.CoroutineContext

interface IPrivacyPolicyScreenComponent {
    val uiState: Value<PrivacyPolicyState>
    fun handleEvent(event: PrivacyPolicyScreenEvent)

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            mainContext: CoroutineContext,
            ioContext: CoroutineContext,
        ): IPrivacyPolicyScreenComponent
    }
}