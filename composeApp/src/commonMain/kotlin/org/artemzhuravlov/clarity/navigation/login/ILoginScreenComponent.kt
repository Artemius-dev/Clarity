package org.artemzhuravlov.clarity.navigation.login

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import kotlin.coroutines.CoroutineContext

interface ILoginScreenComponent {
    fun onEvent(event: LoginScreenEvent)
    val email: Value<String>
    val password: Value<String>
    val validationEmailError: Value<String>
    val validationPasswordError: Value<String>

    fun handleLogin()

    fun interface Factory {
        operator fun invoke( // 2
            componentContext: ComponentContext,
            mainContext: CoroutineContext,
            ioContext: CoroutineContext,
            onNavigateToMainScreen: () -> Unit
        ): ILoginScreenComponent
    }
}