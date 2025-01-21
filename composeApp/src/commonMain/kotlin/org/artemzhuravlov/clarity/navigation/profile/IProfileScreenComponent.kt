package org.artemzhuravlov.clarity.navigation.profile

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.StateFlow
import org.artemzhuravlov.clarity.data.repository.user.IUserRepository
import kotlin.coroutines.CoroutineContext

interface IProfileScreenComponent {
    val uiState: Value<ProfileScreenState>
    fun handleEvent(event: ProfileScreenEvent)

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            mainContext: CoroutineContext,
            ioContext: CoroutineContext,
            onLogout: () -> Unit,
            onNavigateToPoliciesScreen: () -> Unit,
            onNavigateToContactsScreen: () -> Unit,
            onNavigateToPrivacyPolicyScreen: () -> Unit,
            onNavigateToTermsAndConditionsScreen: () -> Unit,
        ): IProfileScreenComponent
    }
}