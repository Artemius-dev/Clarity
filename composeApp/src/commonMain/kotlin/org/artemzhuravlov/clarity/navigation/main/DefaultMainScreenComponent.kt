package org.artemzhuravlov.clarity.navigation.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.artemzhuravlov.clarity.data.repository.main.IUserDataRepository
import kotlin.coroutines.CoroutineContext

class DefaultMainScreenComponent(
    componentContext: ComponentContext,
    mainContext: CoroutineContext,
    private val ioContext: CoroutineContext,
    private val userDataRepository: IUserDataRepository,
    private val onNavigateToContactsScreen: () -> Unit,
    private val onNavigateToInsuredEventScreen: () -> Unit,
    private val onNavigateToClinicsMap: () -> Unit,
): ComponentContext by componentContext, IMainScreenComponent {
    private val scope = CoroutineScope(mainContext + SupervisorJob())

    private val _uiState = MutableValue(MainScreenState())
    override val uiState: Value<MainScreenState> get() = _uiState

    init {
        scope.launch {
            withContext(ioContext) {
                userDataRepository.fetchOffers(onSuccess = { offers ->
                    _uiState.value = _uiState.value.copy(offers = offers)
                })

                userDataRepository.fetchNotifications(onSuccess = { notifications ->
                    _uiState.value = _uiState.value.copy(notifications = notifications)
                })
            }
        }
    }

    override fun handleEvent(event: MainScreenEvent) {
        when (event) {
            is MainScreenEvent.DismissErrorDialog -> {
                _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = null)
            }
            is MainScreenEvent.NavigateToContacts -> {
                scope.launch {
                    onNavigateToContactsScreen()
                }
            }

            MainScreenEvent.NavigateToInsuredEvent -> {
                scope.launch {
                    onNavigateToInsuredEventScreen()
                }
            }

            MainScreenEvent.NavigateToClinicsMap -> {
                scope.launch {
                    onNavigateToClinicsMap()
                }
            }
        }
    }

    private fun loadData() {

    }

    class Factory(
        private val userDataRepository: IUserDataRepository,
    ): IMainScreenComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext,
            mainContext: CoroutineContext,
            ioContext: CoroutineContext,
            onNavigateToContactsScreen: () -> Unit,
            onNavigateToInsuredEventScreen: () -> Unit,
            onNavigateToClinicsList: () -> Unit,
        ): IMainScreenComponent = DefaultMainScreenComponent(
            componentContext = componentContext,
            mainContext = mainContext,
            ioContext = ioContext,
            userDataRepository = userDataRepository,
            onNavigateToContactsScreen = onNavigateToContactsScreen,
            onNavigateToInsuredEventScreen = onNavigateToInsuredEventScreen,
            onNavigateToClinicsMap = onNavigateToClinicsList,
        )
    }
}
