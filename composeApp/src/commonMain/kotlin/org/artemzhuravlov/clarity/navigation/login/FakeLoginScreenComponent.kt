package org.artemzhuravlov.clarity.navigation.login

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value

class FakeLoginScreenComponent : ILoginScreenComponent {
    override fun onEvent(event: LoginScreenEvent) {}
    override val email: Value<String> = MutableValue("")
    override val password: Value<String> = MutableValue("")
    override val validationEmailError: Value<String> = MutableValue("")
    override val validationPasswordError: Value<String> = MutableValue("")
    override fun handleLogin() {}
}