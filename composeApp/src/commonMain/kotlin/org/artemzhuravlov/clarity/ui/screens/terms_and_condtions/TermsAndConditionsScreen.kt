package org.artemzhuravlov.clarity.ui.screens.terms_and_condtions

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import org.artemzhuravlov.clarity.navigation.privacy_policy.PrivacyPolicyScreenEvent
import org.artemzhuravlov.clarity.navigation.terms_and_conditions.ITermsAndConditionsScreenComponent
import org.artemzhuravlov.clarity.navigation.terms_and_conditions.TermsAndConditionsScreenEvent

@Composable
fun TermsAndConditionsScreen(component: ITermsAndConditionsScreenComponent) {
    val uiState by component.uiState.subscribeAsState()
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp,
                top = 16.dp,
                end = 16.dp,
                bottom = 60.dp)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = uiState.content,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(16.dp)
        )
    }

    if (uiState.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }

    if (uiState.error != null && uiState.error!!.isNotEmpty()) {
        AlertDialog(
            onDismissRequest = {
                component.handleEvent(TermsAndConditionsScreenEvent.DismissErrorDialog)
            },
            text = {
                Text(text = uiState.error!!)
            },
            buttons = {
                Button(onClick = {
                    component.handleEvent(TermsAndConditionsScreenEvent.DismissErrorDialog)
                }) {
                    Text(text = "ОК")
                }
            }
        )
    }
}
