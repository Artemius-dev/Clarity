package org.artemzhuravlov.clarity.navigation.policies

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.artemzhuravlov.clarity.data.repository.documents.IDocumentsRepository
import org.artemzhuravlov.clarity.data.repository.policies.IPoliciesRepository
import kotlin.coroutines.CoroutineContext

class DefaultPoliciesScreenComponent(
    componentContext: ComponentContext,
    mainContext: CoroutineContext,
    private val ioContext: CoroutineContext,
    private val onNavigateToContactsScreen: () -> Unit,
    private val policiesRepository: IPoliciesRepository,
    private val documentsRepository: IDocumentsRepository
) : ComponentContext by componentContext, IPoliciesScreenComponent  {
    private val scope = coroutineScope(mainContext + SupervisorJob())

    private val _uiState = MutableValue(PoliciesState())
    override val uiState: Value<PoliciesState> = _uiState

    init {
        loadPolicies()
    }

    override fun handleEvent(event: PoliciesScreenEvent) {
        when (event) {
            is PoliciesScreenEvent.LoadPolicies -> {}

            is PoliciesScreenEvent.NavigateToContacts -> {
                scope.launch {
                    onNavigateToContactsScreen()
                }
            }

            PoliciesScreenEvent.DismissErrorDialog -> {
                // TODO: Add back navigation
                _uiState.value = _uiState.value.copy(isLoading = false, error = null)
            }

            PoliciesScreenEvent.DismissDocumentUri -> {
                _uiState.value = _uiState.value.copy(documentUri = null)
            }

            is PoliciesScreenEvent.GetDocumentForPolicy -> {
                getPolicyDocument(event.policyId)
            }
        }
    }

    private fun getPolicyDocument(policyId: String) {
        scope.launch {
            withContext(ioContext) {
                documentsRepository.getPdfDocumentUrl(policyId = policyId, onSuccess = { documentUri ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        documentUri = documentUri
                    )
                }) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "Сталася помилка при отриманні документі."
                    )
                }
            }
        }
    }

    private fun loadPolicies() {
        scope.launch {
            withContext(ioContext) {
                _uiState.value = _uiState.value.copy(isLoading = true)
                policiesRepository.getPolicies(onSuccess = { policies ->
                    _uiState.value = _uiState.value.copy(isLoading = false, policies = policies)
                }) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "Не можливо загрузити ваші Поліси, спробуйте ще раз пізніше."
                    )
                }
            }
        }
    }

    class Factory(
        private val policiesRepository: IPoliciesRepository,
        private val documentsRepository: IDocumentsRepository
    ) : IPoliciesScreenComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext,
            mainContext: CoroutineContext,
            ioContext: CoroutineContext,
            onNavigateToContactsScreen: () -> Unit,
        ): IPoliciesScreenComponent = DefaultPoliciesScreenComponent(
            componentContext = componentContext,
            mainContext = mainContext,
            ioContext = ioContext,
            onNavigateToContactsScreen = onNavigateToContactsScreen,
            policiesRepository = policiesRepository,
            documentsRepository = documentsRepository
        )
    }
}
