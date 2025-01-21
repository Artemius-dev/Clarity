package org.artemzhuravlov.clarity.ui.screens.policies

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import org.artemzhuravlov.clarity.data.model.policies.Policy
import org.artemzhuravlov.clarity.navigation.policies.IPoliciesScreenComponent
import org.artemzhuravlov.clarity.navigation.policies.PoliciesScreenEvent
import org.artemzhuravlov.clarity.navigation.policies.PoliciesState
import org.artemzhuravlov.clarity.resources.Res
import org.artemzhuravlov.clarity.resources.contact_us
import org.artemzhuravlov.clarity.resources.financial_information
import org.artemzhuravlov.clarity.resources.financial_information_url
import org.artemzhuravlov.clarity.resources.insurance_event_actions
import org.artemzhuravlov.clarity.resources.insurance_event_url
import org.artemzhuravlov.clarity.resources.medical_id
import org.artemzhuravlov.clarity.resources.patient
import org.artemzhuravlov.clarity.resources.policies_title
import org.artemzhuravlov.clarity.resources.validity_period
import org.artemzhuravlov.clarity.resources.view_documents
import org.artemzhuravlov.clarity.ui.composables.PagerIndicator
import org.artemzhuravlov.clarity.ui.utils.openExternalWebPage
import org.artemzhuravlov.clarity.ui.utils.openPdf
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PoliciesScreen(component: IPoliciesScreenComponent) {
    val uiState by component.uiState.subscribeAsState()

    var isOpenInsuranceEvent by remember {
        mutableStateOf(false)
    }

    var isOpenFinancialInformation by remember {
        mutableStateOf(false)
    }

    if (isOpenInsuranceEvent) {
        openExternalWebPage(stringResource(Res.string.insurance_event_url))
        isOpenInsuranceEvent = false
    }

    if (isOpenFinancialInformation) {
        openExternalWebPage(stringResource(Res.string.financial_information_url))
        isOpenFinancialInformation = false
    }

    if (uiState.documentUri != null) {
        openPdf(component.uiState.value.documentUri!!)
        component.handleEvent(PoliciesScreenEvent.DismissDocumentUri)
    }

    val policiesPagerState = rememberPagerState(pageCount = {
        uiState.policies.size
    })

    PrivacyScreen(
        policies = uiState.policies,
        isLoading = uiState.isLoading,
        error = uiState.error,
        policiesPagerState = policiesPagerState,
        isOpenInsuranceEvent = {
            isOpenInsuranceEvent = true
        },
        isOpenFinancialInformation = {
            isOpenFinancialInformation = true
        },
        onNavigateToContacts = {
            component.handleEvent(PoliciesScreenEvent.NavigateToContacts)
        },
        onOpenDocumentForPolicy = {
            component.handleEvent(
                PoliciesScreenEvent.GetDocumentForPolicy(
                    uiState.policies[policiesPagerState.currentPage].policyNumber
                )
            )
        }
    )

    if (uiState.error != null && uiState.error!!.isNotEmpty()) {
        AlertDialog(
            onDismissRequest = {
                component.handleEvent(PoliciesScreenEvent.DismissErrorDialog)
            },
            text = {
                Text(text = uiState.error!!)
            },
            buttons = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(onClick = {
                        component.handleEvent(PoliciesScreenEvent.DismissErrorDialog)
                    }) {
                        Text(text = "ОК")
                    }
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PrivacyScreen(
    policies: List<Policy> = emptyList(),
    isLoading: Boolean,
    error: String? = null,
    policiesPagerState: PagerState,
    isOpenInsuranceEvent: () -> Unit,
    isOpenFinancialInformation: () -> Unit,
    onNavigateToContacts: () -> Unit,
    onOpenDocumentForPolicy: () -> Unit,
) {
    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    } else if (error.isNullOrEmpty()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(Res.string.policies_title)) },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        ) { padding ->
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    HorizontalPager(
                        state = policiesPagerState,
                        beyondViewportPageCount = 2,
                        pageSpacing = (-24).dp,
                        contentPadding = PaddingValues(horizontal = 14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    ) { page ->
                        val card = policies[page]

                        Box(
                            modifier = Modifier
                                .width(340.dp)
                                .height(165.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color(card.backgroundColor))
                        ) {
                            PolicyCard(card)
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        PagerIndicator(pagerState = policiesPagerState)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                        ActionItem(
                            icon = Icons.Default.Call,
                            label = stringResource(Res.string.contact_us),
                            onClick = {
                                onNavigateToContacts.invoke()
                            }
                        )
                        ActionItem(
                            icon = Icons.Default.Warning,
                            label = stringResource(Res.string.insurance_event_actions),
                            onClick = {
                                isOpenInsuranceEvent.invoke()
                            }
                        )
                        ActionItem(
                            icon = Icons.Default.AttachMoney,
                            label = stringResource(Res.string.financial_information),
                            onClick = {
                                isOpenFinancialInformation.invoke()
                            }
                        )
                        ActionItem(
                            icon = Icons.Default.Description,
                            label = stringResource(Res.string.view_documents),
                            onClick = {
                                onOpenDocumentForPolicy.invoke()
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PolicyCard(card: Policy) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(
            text = card.program,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(Res.string.patient, card.patientName),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Text(
            text = stringResource(Res.string.medical_id, card.medicalId),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Text(
            text = stringResource(Res.string.validity_period, card.validityPeriod),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
private fun ActionItem(icon: ImageVector, label: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = label, style = MaterialTheme.typography.bodyLarge)
    }
}
