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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import org.artemzhuravlov.clarity.ui.composables.DefaultOutlinedTextField
import org.artemzhuravlov.clarity.ui.composables.PlaceholderText

@Composable
fun AddressDetailsScreen(modifier: Modifier = Modifier, onNext: () -> Unit, onBack: () -> Unit) {
    // State for form fields
    var actualAddress by rememberSaveable { mutableStateOf("") }
    var registeredAddress by rememberSaveable { mutableStateOf("") }
    var postalCode by rememberSaveable { mutableStateOf("") }
    var city by rememberSaveable { mutableStateOf("") }
    var region by rememberSaveable { mutableStateOf("") }
    var sameAsActualAddress by rememberSaveable { mutableStateOf(false) }

    // Validation states
    var actualAddressError by rememberSaveable { mutableStateOf(false) }
    var registeredAddressError by rememberSaveable { mutableStateOf(false) }
    var postalCodeError by rememberSaveable { mutableStateOf(false) }
    var cityError by rememberSaveable { mutableStateOf(false) }
    var regionError by rememberSaveable { mutableStateOf(false) }

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
            Text("Адреса проживання", style = MaterialTheme.typography.h5)

            // Actual Address
            DefaultOutlinedTextField(
                value = actualAddress,
                onValueChange = {
                    actualAddress = it
                    actualAddressError = actualAddress.isBlank()
                },
                label = { Text("Фактична адреса") },
                placeholder = { PlaceholderText("Вулиця, будинок, квартира") },
                supportingText = {
                    Text(
                        text = "Це поле є обов'язковим*",
                        color = Color.Gray,
                        style = MaterialTheme.typography.caption
                    )
                },
                isError = actualAddressError
            )
            if (actualAddressError) {
                //TODO: Add error text
            }

            // Checkbox for Same Address
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    if (!sameAsActualAddress) registeredAddressError = false
                    sameAsActualAddress = !sameAsActualAddress
                }
            ) {
                Checkbox(
                    checked = sameAsActualAddress,
                    onCheckedChange = {
                        if (it) registeredAddressError = false
                        sameAsActualAddress = it
                    }
                )
                Text("Реєстраційна адреса така ж, як фактична")
            }

            // Registered Address (only visible if checkbox is unchecked)
            if (!sameAsActualAddress) {
                DefaultOutlinedTextField(
                    value = registeredAddress,
                    onValueChange = {
                        registeredAddress = it
                        registeredAddressError = registeredAddress.isBlank()
                    },
                    label = { Text("Реєстраційна адреса") },
                    placeholder = { PlaceholderText("Вулиця, будинок, квартира") },
                    supportingText = {
                        Text(
                            text = "Це поле є обов'язковим, якщо адреси різні*",
                            color = Color.Gray,
                            style = MaterialTheme.typography.caption
                        )
                    },
                    isError = registeredAddressError
                )
                if (registeredAddressError) {
                    //TODO: Add error text
                }
            }

            // Postal Code
            DefaultOutlinedTextField(
                value = postalCode,
                onValueChange = {
                    postalCode = it
                    postalCodeError = postalCode.isBlank() || !postalCode.matches(Regex("\\d{5}"))
                },
                label = { Text("Поштовий індекс") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                placeholder = { PlaceholderText("Наприклад, 02000") },
                supportingText = {
                    Text(
                        text = "Це поле є обов'язковим*",
                        color = Color.Gray,
                        style = MaterialTheme.typography.caption
                    )
                },
                isError = postalCodeError
            )
            if (postalCodeError) {
                Text(
                    text = "Введіть дійсний 5-значний поштовий індекс.",
                    color = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.caption
                )
            }

            // City
            DefaultOutlinedTextField(
                value = city,
                onValueChange = {
                    city = it
                    cityError = city.isBlank()
                },
                label = { Text("Місто") },
                placeholder = { PlaceholderText("Наприклад, Київ") },
                supportingText = {
                    Text(
                        text = "Це поле є обов'язковим*",
                        color = Color.Gray,
                        style = MaterialTheme.typography.caption
                    )
                },
                isError = cityError
            )
            if (cityError) {
                //TODO: Add error text
            }

            DefaultOutlinedTextField(
                value = region,
                onValueChange = {
                    region = it
                    regionError = region.isBlank()
                },
                label = { Text("Область") },
                placeholder = { PlaceholderText("Наприклад, Київська") },
                supportingText = {
                    Text(
                        text = "Це поле є обов'язковим*",
                        color = Color.Gray,
                        style = MaterialTheme.typography.caption
                    )
                },
                isError = regionError
            )
            if (regionError) {
                //TODO: Add error text
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
                        onClick = {
                            // Trigger validation on submission
                            actualAddressError = actualAddress.isBlank()
                            registeredAddressError =
                                !sameAsActualAddress && registeredAddress.isBlank()
                            postalCodeError =
                                postalCode.isBlank() || !postalCode.matches(Regex("\\d{5}"))
                            cityError = city.isBlank()
                            regionError = region.isBlank()

                            if (!actualAddressError && !registeredAddressError && !postalCodeError && !cityError && !regionError) {
                                onNext()
                            }
                        },
                        enabled = !actualAddressError && !registeredAddressError
                                && !postalCodeError && !cityError && !regionError
                                && actualAddress.isNotBlank() && postalCode.isNotBlank()
                                && city.isNotBlank() && region.isNotBlank()
                                  && (registeredAddress.isNotBlank() || sameAsActualAddress)
                    ) {
                        Text("Далі")
                    }
                }
            }
        }
    }
}
