package org.artemzhuravlov.clarity.navigation.profile

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.artemzhuravlov.clarity.data.model.user.copy
import org.artemzhuravlov.clarity.data.repository.login.ILoginRepository
import org.artemzhuravlov.clarity.data.repository.user.IUserRepository
import kotlin.coroutines.CoroutineContext

class DefaultProfileScreenComponent(
    componentContext: ComponentContext,
    mainContext: CoroutineContext,
    private val ioContext: CoroutineContext,
    private val loginRepository: ILoginRepository,
    private val userRepository: IUserRepository,
    private val onLogout: () -> Unit,
    private val onNavigateToPoliciesScreen: () -> Unit,
    private val onNavigateToContactsScreen: () -> Unit,
    private val onNavigateToPrivacyPolicyScreen: () -> Unit,
    private val onNavigateToTermsAndConditionsScreen: () -> Unit,
) : ComponentContext by componentContext, IProfileScreenComponent {
    private val scope = coroutineScope(mainContext + SupervisorJob())

    private val _uiState = MutableValue(ProfileScreenState())
    override val uiState: Value<ProfileScreenState> = _uiState

    init {
        loadUserProfile()
    }

    override fun handleEvent(event: ProfileScreenEvent) {
        when (event) {
            is ProfileScreenEvent.LoadProfile -> loadUserProfile()
            is ProfileScreenEvent.DismissErrorDialog -> {
                // TODO: Add back navigation
                _uiState.value = _uiState.value.copy(error = null)
            }

            ProfileScreenEvent.OnLogout -> {
                scope.launch {
                    withContext(ioContext) {
                        loginRepository.logout(onSuccess = {
                            scope.launch {
                                onLogout()
                            }
                        })
                    }
                }
            }

            ProfileScreenEvent.NavigateToPolicies -> {
                scope.launch {
                    onNavigateToPoliciesScreen()
                }
            }

            ProfileScreenEvent.NavigateToContacts -> {
                scope.launch {
                    onNavigateToContactsScreen()
                }
            }
            ProfileScreenEvent.NavigateToPrivacyPolicy -> {
                scope.launch {
                    onNavigateToPrivacyPolicyScreen()
                }
            }
            ProfileScreenEvent.NavigateToTermsAndConditions -> {
                scope.launch {
                    onNavigateToTermsAndConditionsScreen()
                }
            }
        }
    }

    private fun loadUserProfile() {
        scope.launch {
            withContext(ioContext) {
                _uiState.value = _uiState.value.copy(isLoading = true)
                userRepository.getUser(onSuccess = { user ->
                    _uiState.value = _uiState.value.copy(isLoading = false, user = user)
                }) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "Не можливо загрузити ваш профіль, спробуйте ще раз пізніше."
                    )
                }
            }
        }
    }

    private fun updateUserProfile(displayName: String) {
        scope.launch {
            withContext(ioContext) {
                try {
                    _uiState.value = _uiState.value.copy(isLoading = true)
                    val user = _uiState.value.user ?: return@withContext
                    val updatedUser = user.copy(
                        displayName = displayName
                    )

                    userRepository.updateUser(user = updatedUser, onSuccess = {
                        _uiState.value = _uiState.value.copy(isLoading = false)
                    }) {

                    }
                } catch (e: Exception) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "Сталася помилка при оновленні вашого профілю, спробуйте ще раз пізніше."
                    )
                }
            }
        }
    }


    class Factory(
        private val loginRepository: ILoginRepository,
        private val userRepository: IUserRepository
    ) : IProfileScreenComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext,
            mainContext: CoroutineContext,
            ioContext: CoroutineContext,
            onLogout: () -> Unit,
            onNavigateToPoliciesScreen: () -> Unit,
            onNavigateToContactsScreen: () -> Unit,
            onNavigateToPrivacyPolicyScreen: () -> Unit,
            onNavigateToTermsAndConditionsScreen: () -> Unit,
        ): IProfileScreenComponent = DefaultProfileScreenComponent(
            componentContext = componentContext,
            mainContext = mainContext,
            ioContext = ioContext,
            loginRepository = loginRepository,
            userRepository = userRepository,
            onLogout = onLogout,
            onNavigateToPoliciesScreen = onNavigateToPoliciesScreen,
            onNavigateToContactsScreen = onNavigateToContactsScreen,
            onNavigateToPrivacyPolicyScreen = onNavigateToPrivacyPolicyScreen,
            onNavigateToTermsAndConditionsScreen = onNavigateToTermsAndConditionsScreen,
        )
    }
}