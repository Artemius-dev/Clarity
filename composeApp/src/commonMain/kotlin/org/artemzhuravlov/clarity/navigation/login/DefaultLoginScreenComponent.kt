package org.artemzhuravlov.clarity.navigation.login

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import isEmailValid
import isPasswordValid
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.artemzhuravlov.clarity.data.repository.login.ILoginRepository
import kotlin.coroutines.CoroutineContext

class DefaultLoginScreenComponent(
    componentContext: ComponentContext,
    mainContext: CoroutineContext,
    private val ioContext: CoroutineContext,
    private val loginRepository: ILoginRepository,
    private val onNavigateToMainScreen: () -> Unit
) : ComponentContext by componentContext, ILoginScreenComponent {

    private val scope = coroutineScope(mainContext + SupervisorJob())

    private val maxEmailLength = 50
    private val maxPasswordLength = 20

    private var _email = MutableValue("")
    override val email: Value<String> = _email

    private var _password = MutableValue("")
    override val password: Value<String> = _password

    private var _validationEmailError = MutableValue("")
    override val validationEmailError: Value<String> = _validationEmailError

    private var _validationPasswordError = MutableValue("")
    override val validationPasswordError: Value<String> = _validationPasswordError

    init {
        scope.launch {
            withContext(ioContext) {
                loginRepository.checkIsUserLoggedIn() {
                    scope.launch {
                        onNavigateToMainScreen()
                    }
                }
            }
        }
    }

    override fun onEvent(event: LoginScreenEvent) {
        when (event) {
            LoginScreenEvent.ClickLogin -> handleLogin()
            is LoginScreenEvent.UpdateEmail -> {
                if (event.email.length <= maxEmailLength) {
                    _validationEmailError.value = ""
                    _validationPasswordError.value = ""
                    _email.value = event.email
                } else {
                    _validationEmailError.value = "Пошта не може перевищувати $maxEmailLength знаків."
                }
            }

            is LoginScreenEvent.UpdatePassword -> {
                if (event.password.length <= maxPasswordLength) {
                    _validationEmailError.value = ""
                    _validationPasswordError.value = ""
                    _password.value = event.password
                } else {
                    _validationPasswordError.value = "Пароль не може перевищувати $maxPasswordLength знаків."
                }
            }
        }
    }

    private fun validateEmail(email: String) {
        if (!isEmailValid(email)) {
            _validationEmailError.value = "Не вірний формат пошти."
        } else {
            _validationEmailError.value = ""
        }
    }

    override fun handleLogin() {
        validateEmail(email.value)
        if (isEmailValid(email.value) && isPasswordValid(password.value)) {
            scope.launch {
                withContext(ioContext) {
                    loginRepository.login(
                        email = email.value,
                        password = password.value,
                        onSuccess = {
                            scope.launch {
                                onNavigateToMainScreen()
                            }
                        }) { exception ->
                        _validationPasswordError.value = exception.message.toString()
                    }
                }
            }
        } else {
            _validationEmailError.value = "Не вірна пошта чи пароль, спробуйте ще раз."
            _validationPasswordError.value = "Не вірна пошта чи пароль, спробуйте ще раз."
        }
    }

    class Factory(
        private val loginRepository: ILoginRepository
    ) : ILoginScreenComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext,
            mainContext: CoroutineContext,
            ioContext: CoroutineContext,
            onNavigateToMainScreen: () -> Unit
        ): ILoginScreenComponent = DefaultLoginScreenComponent(
            componentContext = componentContext,
            mainContext = mainContext,
            ioContext = ioContext,
            loginRepository = loginRepository,
            onNavigateToMainScreen = onNavigateToMainScreen
        )
    }
}

