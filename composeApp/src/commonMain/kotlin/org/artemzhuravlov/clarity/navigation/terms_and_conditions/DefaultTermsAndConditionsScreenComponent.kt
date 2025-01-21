package org.artemzhuravlov.clarity.navigation.terms_and_conditions

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.artemzhuravlov.clarity.data.repository.config.IConfigRepository
import org.artemzhuravlov.clarity.navigation.privacy_policy.DefaultPrivacyPolicyScreenComponent
import kotlin.coroutines.CoroutineContext

class DefaultTermsAndConditionsScreenComponent(
    componentContext: ComponentContext,
    mainContext: CoroutineContext,
    private val ioContext: CoroutineContext,
    private val configRepository: IConfigRepository
) : ComponentContext by componentContext, ITermsAndConditionsScreenComponent {
    private val scope = coroutineScope(mainContext + SupervisorJob())

    private val _uiState = MutableValue(TermsAndConditionsState())
    override val uiState: Value<TermsAndConditionsState> get() = _uiState

    init {
        loadTerms()
    }

    override fun handleEvent(event: TermsAndConditionsScreenEvent) {
        when (event) {
            is TermsAndConditionsScreenEvent.LoadTermsAndConditions -> loadTerms()
            TermsAndConditionsScreenEvent.DismissErrorDialog -> {
                _uiState.value = _uiState.value.copy(isLoading = false, error = null)
            }
        }
    }

    private fun loadTerms() {
        scope.launch {
            withContext(ioContext) {
                _uiState.value = _uiState.value.copy(isLoading = true)
                configRepository.getTermsAndConditions(onSuccess = { content ->
                    _uiState.value =
                        _uiState.value.copy(isLoading = false, content = content.replace("\\n", "\n"))
                }) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "Не вдалося завантажити Умови використання. Спробуйте ще раз, пізніше."
                    )
                }
            }
        }
    }

    class Factory(
        private val configRepository: IConfigRepository,
    ) : ITermsAndConditionsScreenComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext,
            mainContext: CoroutineContext,
            ioContext: CoroutineContext,
        ): ITermsAndConditionsScreenComponent = DefaultTermsAndConditionsScreenComponent(
            componentContext = componentContext,
            mainContext = mainContext,
            ioContext = ioContext,
            configRepository = configRepository,
        )
    }
}
