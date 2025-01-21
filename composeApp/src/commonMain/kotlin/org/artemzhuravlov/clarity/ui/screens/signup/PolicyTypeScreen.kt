package org.artemzhuravlov.clarity.ui.screens.signup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PolicyTypeScreen(modifier: Modifier = Modifier, onNext: () -> Unit, onBack: () -> Unit) {
    var selectedPolicyType by rememberSaveable { mutableStateOf("") }
    val policyTypes = listOf("Здоров’я", "Життя", "Автоцивілка", "Інше")

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = modifier
                .wrapContentWidth()
                .fillMaxHeight()
                .weight(1f)
                .width(IntrinsicSize.Max)
                .padding(16.dp)
                .padding(bottom = 36.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text("Виберіть тип страхового полісу", style = MaterialTheme.typography.h5)

            policyTypes.forEach { type ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectedPolicyType = type }
                        .padding(vertical = 8.dp)
                ) {
                    RadioButton(
                        selected = selectedPolicyType == type,
                        onClick = { selectedPolicyType = type }
                    )
                    Text(type, style = MaterialTheme.typography.body1)
                }
            }

            Box(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
                Row(
                    modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(onClick = onBack) {
                        Text("Назад")
                    }
                    Button(
                        onClick = { if (selectedPolicyType.isNotEmpty()) onNext() },
                        enabled = selectedPolicyType.isNotEmpty()
                    ) {
                        Text("Далі")
                    }
                }
            }
        }
    }
}
