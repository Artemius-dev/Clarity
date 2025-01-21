package org.artemzhuravlov.clarity.navigation.privacy_policy

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.artemzhuravlov.clarity.data.repository.config.IConfigRepository
import kotlin.coroutines.CoroutineContext

class DefaultPrivacyPolicyScreenComponent(
    componentContext: ComponentContext,
    mainContext: CoroutineContext,
    private val ioContext: CoroutineContext,
    private val configRepository: IConfigRepository
) : ComponentContext by componentContext, IPrivacyPolicyScreenComponent {
    private val scope = coroutineScope(mainContext + SupervisorJob())

    private val _uiState = MutableValue(PrivacyPolicyState())
    override val uiState: Value<PrivacyPolicyState> = _uiState

    init {
        loadPolicy()
    }

    override fun handleEvent(event: PrivacyPolicyScreenEvent) {
        when (event) {
            is PrivacyPolicyScreenEvent.LoadPolicy -> loadPolicy()
            PrivacyPolicyScreenEvent.DismissErrorDialog -> {
                _uiState.value = _uiState.value.copy(isLoading = false, error = null)
            }
        }
    }

    private fun loadPolicy() {
        scope.launch {
            withContext(ioContext) {
                _uiState.value = _uiState.value.copy(isLoading = true)
                configRepository.getPrivacyPolicy(onSuccess = { content ->
                    _uiState.value =
                        _uiState.value.copy(isLoading = false, content = content.replace("\\n", "\n"))
                }) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "Не вдалося завантажити Політику конфіденційності. Спробуйте ще раз, пізніше."
                    )
                }
            }
        }
    }

    class Factory(
        private val configRepository: IConfigRepository,
    ) : IPrivacyPolicyScreenComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext,
            mainContext: CoroutineContext,
            ioContext: CoroutineContext,
        ): IPrivacyPolicyScreenComponent = DefaultPrivacyPolicyScreenComponent(
            componentContext = componentContext,
            mainContext = mainContext,
            ioContext = ioContext,
            configRepository = configRepository,
        )
    }
}
