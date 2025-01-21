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
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.artemzhuravlov.clarity.ui.composables.DefaultOutlinedTextField
import org.artemzhuravlov.clarity.ui.utils.openExternalWebPage

@Composable
fun AdditionalInformationScreen(
    modifier: Modifier = Modifier,
    onSubmit: () -> Unit,
    onBack: () -> Unit
) {
    // State for fields
    var beneficiaryName by rememberSaveable { mutableStateOf("") }
    var beneficiaryRelation by rememberSaveable { mutableStateOf("") }
    var medicalConditions by rememberSaveable { mutableStateOf("") }
    var termsAccepted by rememberSaveable { mutableStateOf(false) }
    var dataConsentAccepted by rememberSaveable { mutableStateOf(false) }

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
            // Beneficiary Details
            Text("Дані вигодонабувача", style = MaterialTheme.typography.h5)

            DefaultOutlinedTextField(
                value = beneficiaryName,
                onValueChange = { beneficiaryName = it },
                label = { Text("Ім'я вигодонабувача") },
                placeholder = { Text("Наприклад, Іван Іванович") },
                modifier = Modifier.fillMaxWidth()
            )

            DefaultOutlinedTextField(
                value = beneficiaryRelation,
                onValueChange = { beneficiaryRelation = it },
                label = { Text("Відношення до вигодонабувача") },
                placeholder = { Text("Наприклад, Чоловік/Дружина") },
                modifier = Modifier.fillMaxWidth()
            )

            // Medical Records
            Text("Медичні дані", style = MaterialTheme.typography.h5)

            DefaultOutlinedTextField(
                value = medicalConditions,
                onValueChange = { medicalConditions = it },
                label = { Text("Хронічні захворювання чи ризики") },
                placeholder = { Text("Наприклад, діабет, гіпертонія") },
                modifier = Modifier.fillMaxWidth()
            )

            // Legal Agreements
            Text("Юридичні угоди", style = MaterialTheme.typography.h5)

            LegalAgreementsWithLinks(termsAccepted = termsAccepted,
                onTermsChecked = { termsAccepted = it },
                dataConsentAccepted = dataConsentAccepted,
                onDataConsentChecked = { dataConsentAccepted = it })

            Box(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
                Row(
                    modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(onClick = onBack) {
                        Text("Назад")
                    }
                    Button(
                        onClick = {
                            if (beneficiaryName.isNotBlank() && beneficiaryRelation.isNotBlank() &&
                                termsAccepted && dataConsentAccepted
                            ) {
                                onSubmit()
                            }
                        },
                        enabled = beneficiaryName.isNotBlank() && beneficiaryRelation.isNotBlank() &&
                                termsAccepted && dataConsentAccepted
                    ) {
                        Text("Підтвердити")
                    }
                }
            }
        }
    }
}

@Composable
fun LegalAgreementsWithLinks(
    termsAccepted: Boolean,
    onTermsChecked: (Boolean) -> Unit,
    dataConsentAccepted: Boolean,
    onDataConsentChecked: (Boolean) -> Unit
) {
    var openExternalWebPage by remember {
        mutableStateOf("")
    }

    if (openExternalWebPage.isNotBlank()) {
        openExternalWebPage(openExternalWebPage)
        openExternalWebPage = ""
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = termsAccepted,
                onCheckedChange = onTermsChecked
            )
            BasicText(
                text = buildAnnotatedString {
                    append("Я погоджуюсь з ")
                    pushStringAnnotation(tag = "TERMS", annotation = "https://www.google.com")
                    withStyle(
                        style = SpanStyle(
                            color = Color.Blue,
                            textDecoration = TextDecoration.Underline
                        )
                    ) {
                        append("умовами")
                    }
                    pop()
                },
                modifier = Modifier
                    .padding(start = 8.dp)
                    .clickable {
                        openExternalWebPage = "https://www.google.com"
                    }
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = dataConsentAccepted,
                onCheckedChange = onDataConsentChecked
            )
            BasicText(
                text = buildAnnotatedString {
                    append("Я погоджуюсь на обробку ")
                    pushStringAnnotation(
                        tag = "DATA_CONSENT",
                        annotation = "https://www.google.com"
                    )
                    withStyle(
                        style = SpanStyle(
                            color = Color.Blue,
                            textDecoration = TextDecoration.Underline
                        )
                    ) {
                        append("персональних даних")
                    }
                    pop()
                },
                modifier = Modifier
                    .padding(start = 8.dp)
                    .clickable {
                        openExternalWebPage = "https://www.google.com"
                    }
            )
        }
    }
}
