package org.artemzhuravlov.clarity.ui.screens.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.automirrored.outlined.Assignment
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.DoorFront
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.Support
import androidx.compose.material.icons.outlined.DoorFront
import androidx.compose.material.icons.outlined.Gavel
import androidx.compose.material.icons.outlined.PrivacyTip
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import org.artemzhuravlov.clarity.data.model.user.AuthenticatedUser
import org.artemzhuravlov.clarity.navigation.profile.IProfileScreenComponent
import org.artemzhuravlov.clarity.navigation.profile.ProfileScreenEvent

@Composable
fun ProfileScreen(component: IProfileScreenComponent) {
    val uiState by component.uiState.subscribeAsState()
    ProfileScreen(
        user = uiState.user,
        isLoading = uiState.isLoading,
        error = uiState.error,
        onViewPolicies = {
            component.handleEvent(ProfileScreenEvent.NavigateToPolicies)
        },
        onContactSupport = {
            component.handleEvent(ProfileScreenEvent.NavigateToContacts)
        },
        onPrivacyPolicy = {
            component.handleEvent(ProfileScreenEvent.NavigateToPrivacyPolicy)
        },
        onTermsOfService = {
            component.handleEvent(ProfileScreenEvent.NavigateToTermsAndConditions)
        },
        onLogout = {
            component.handleEvent(ProfileScreenEvent.OnLogout)
        },
    )
    if (uiState.error != null && uiState.error!!.isNotEmpty()) {
        AlertDialog(
            onDismissRequest = {
                component.handleEvent(ProfileScreenEvent.DismissErrorDialog)
            },
            text = {
                Text(text = uiState.error!!)
            },
            buttons = {
                Button(onClick = {
                    component.handleEvent(ProfileScreenEvent.DismissErrorDialog)
                }) {
                    Text(text = "ОК")
                }
            }
        )
    }
}

@Composable
private fun ProfileScreen(
    user: AuthenticatedUser? = null,
    isLoading: Boolean = false,
    error: String? = null,
    onViewPolicies: () -> Unit,
    onContactSupport: () -> Unit,
    onPrivacyPolicy: () -> Unit,
    onTermsOfService: () -> Unit,
    onLogout: () -> Unit,
) {
    val scrollState = rememberScrollState()

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    } else if (error.isNullOrEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Profile Header
            if (user != null && user.displayName!!.isNotBlank() && user.email!!.isNotBlank()) {
                ProfileHeader(
                    userName = user.displayName!!,
                    email = user.email!!
                )
            }

            HorizontalDivider()

            // Features Section
            FeatureItem(
                icon = Icons.AutoMirrored.Outlined.Assignment,
                title = "Мої поліси",
                onClick = onViewPolicies
            )
            FeatureItem(icon = Icons.Default.Support, title = "Підтримка", onClick = onContactSupport)

            HorizontalDivider()

            // Links Section
            FeatureItem(
                icon = Icons.Outlined.PrivacyTip,
                title = "Політика конфіденційності",
                onClick = onPrivacyPolicy
            )
            FeatureItem(
                icon = Icons.Outlined.Gavel,
                title = "Умови використання",
                onClick = onTermsOfService
            )

            HorizontalDivider()

            // Logout Section
            FeatureItem(
                icon = Icons.Outlined.DoorFront,
                title = "Вийти з додатку",
                color = MaterialTheme.colorScheme.error,
                onClick = onLogout
            )

        }
    }
}

@Composable
fun ProfileHeader(userName: String, email: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "Привіт, $userName", style = MaterialTheme.typography.bodyLarge)
        Text(
            text = "Ваша пошта: $email",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    }
}

@Composable
fun FeatureItem(icon: ImageVector, title: String, color: Color = Color.Black, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = title, modifier = Modifier.size(24.dp), tint = color)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = title, style = MaterialTheme.typography.bodyMedium, color = color)
    }
}
