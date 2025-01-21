package org.artemzhuravlov.clarity.navigation.login

sealed interface LoginScreenEvent {
    data object ClickLogin: LoginScreenEvent
    data class UpdateEmail(val email: String): LoginScreenEvent
    data class UpdatePassword(val password: String): LoginScreenEvent
}