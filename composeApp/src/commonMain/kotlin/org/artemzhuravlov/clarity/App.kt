package org.artemzhuravlov.clarity

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import org.artemzhuravlov.clarity.navigation.root.DefaultRootComponent
import org.artemzhuravlov.clarity.ui.screens.maps.clinic_details.ClinicDetailsScreen
import org.artemzhuravlov.clarity.navigation.root.IRootComponent
import org.artemzhuravlov.clarity.ui.screens.contacts.ContactsScreen
import org.artemzhuravlov.clarity.ui.screens.login.LoginScreen
import org.artemzhuravlov.clarity.ui.screens.main.MainScreen
import org.artemzhuravlov.clarity.ui.screens.maps.clinics_list.ClinicsListScreen
import org.artemzhuravlov.clarity.ui.screens.maps.clinics_map.ClinicsMapScreen
import org.artemzhuravlov.clarity.ui.screens.maps.fullscreen_map.MapScreen
import org.artemzhuravlov.clarity.ui.screens.policies.PoliciesScreen
import org.artemzhuravlov.clarity.ui.screens.privacy_policy.PrivacyPolicyScreen
import org.artemzhuravlov.clarity.ui.screens.profile.ProfileScreen
import org.artemzhuravlov.clarity.ui.screens.terms_and_condtions.TermsAndConditionsScreen
import org.artemzhuravlov.clarity.ui.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

data class ScreensBottom(
    val name: String,
    val openScreen: () -> Unit,
    val configuration: DefaultRootComponent.Configuration
)

@Composable
@Preview
fun App(rootComponent: IRootComponent) {
    val childStack by rootComponent.childStack.subscribeAsState()
    val selectedItem = remember { mutableStateOf(0) }

    val screens = listOf(
        ScreensBottom("Стрічка", rootComponent::navigateToMain, DefaultRootComponent.Configuration.MainScreen),
//        ScreensBottom("Договори", rootComponent::navigateToDocuments, DefaultRootComponent.Configuration.DocumentsScreen),
//        ScreensBottom("Магазин", rootComponent::navigateToShop, DefaultRootComponent.Configuration.ShopScreen),
        ScreensBottom("Медецина", rootComponent::navigateToClinicsMap, DefaultRootComponent.Configuration.ClinicsMapScreen),
        ScreensBottom("Профіль", rootComponent::navigateToProfile, DefaultRootComponent.Configuration.ProfileScreen)
    )

    // Update selected item based on the current screen
    LaunchedEffect(childStack.items) {
        val currentConfiguration = childStack.items.lastOrNull()?.configuration
        val currentIndex = selectedItem.value
        val newIndex = screens.indexOfFirst { it.configuration == currentConfiguration }
        selectedItem.value = if (newIndex == -1) currentIndex else newIndex
    }

    AppTheme {
        Scaffold(modifier = Modifier.safeDrawingPadding(),
            bottomBar = {
                if (childStack.items.any { it.configuration is DefaultRootComponent.Configuration.LoginScreen }
                        .not() && childStack.items.isNotEmpty()) {
                    BottomAppBar(
                        modifier = Modifier.fillMaxWidth().height(60.dp),
                        actions = {
                            screens.forEachIndexed { index, screensBottom ->
                                NavigationBarItem(
                                    icon = {
                                        when (screensBottom.name) {
                                            "Стрічка" -> Icon(
                                                Icons.Outlined.Home,
                                                contentDescription = null
                                            )

                                            "Медецина" -> Icon(
                                                Icons.Outlined.Map,
                                                contentDescription = null
                                            )

                                            "Профіль" -> Icon(
                                                Icons.Outlined.PersonOutline,
                                                contentDescription = null
                                            )
                                        }
                                    },
                                    label = {
                                        Text(
                                            text = screensBottom.name,
                                            style = MaterialTheme.typography.labelMedium,
                                            fontWeight = FontWeight.Light
                                        )
                                    },
                                    selected = selectedItem.value == index,
                                    onClick = {
                                        screensBottom.openScreen()
                                    },
                                    colors = NavigationBarItemDefaults.colors(selectedIconColor = MaterialTheme.colorScheme.secondary)
                                )
                            }
                        }
                    )
                }
            }) {
            Children(
                stack = childStack,
                animation = stackAnimation(animator = slide())
            ) { child ->
                when (val instance = child.instance) {
                    is IRootComponent.Child.LoginScreen -> LoginScreen(component = instance.component)
                    is IRootComponent.Child.MainScreen -> MainScreen(component = instance.component)
                    is IRootComponent.Child.ClinicsMapScreen -> ClinicsMapScreen(component = instance.component)
                    is IRootComponent.Child.ClinicsListScreen -> ClinicsListScreen(component = instance.component)
                    is IRootComponent.Child.ClinicDetailsScreen -> ClinicDetailsScreen(component = instance.component)
                    is IRootComponent.Child.MapScreen -> MapScreen(component = instance.component)
                    is IRootComponent.Child.ProfileScreen -> ProfileScreen(component = instance.component)
                    is IRootComponent.Child.ContactsScreen -> ContactsScreen(component = instance.component)
                    is IRootComponent.Child.PrivacyPolicyScreen -> PrivacyPolicyScreen(component = instance.component)
                    is IRootComponent.Child.TermsAndConditionsScreen -> TermsAndConditionsScreen(component = instance.component)
                    is IRootComponent.Child.PoliciesScreen -> PoliciesScreen(component = instance.component)
                }
            }
        }
    }
}