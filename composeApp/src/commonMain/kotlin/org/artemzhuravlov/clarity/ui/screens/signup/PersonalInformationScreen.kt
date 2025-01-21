package org.artemzhuravlov.clarity.ui.screens.signup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import kotlinx.datetime.LocalDate
import org.artemzhuravlov.clarity.ui.composables.DefaultOutlinedTextField
import org.artemzhuravlov.clarity.ui.composables.GenderSelector
import org.artemzhuravlov.clarity.ui.composables.PhoneNumberTextField
import org.artemzhuravlov.clarity.ui.composables.PlaceholderText
import org.artemzhuravlov.clarity.ui.composables.WheelDatePickerBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalInformationScreen(modifier: Modifier = Modifier, onNext: () -> Unit) {
    var fullName by rememberSaveable { mutableStateOf("") }
    var dateOfBirth by rememberSaveable { mutableStateOf<LocalDate?>(null) }
    var idCode by rememberSaveable { mutableStateOf("") }
    var phoneNumber by rememberSaveable { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    var gender by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var passport by rememberSaveable { mutableStateOf("") }
    var emailError by rememberSaveable { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = modifier
                .wrapContentSize()
                .padding(16.dp)
                .padding(bottom = 36.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),

            ) {
            Text("Особиста інформація", style = MaterialTheme.typography.h5)

            DefaultOutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                label = { Text("Прізвище, Ім’я, По батькові") }
            )

            DefaultOutlinedTextField(
                value = if (dateOfBirth != null) dateOfBirth.toString() else "",
                onValueChange = {},
                label = { Text("Дата народження") },
                readOnly = true,
                enabled = false,
                modifier = Modifier.clickable {
                    showDatePicker = true
                },
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = Color.Black,
                    disabledBorderColor = Color.Black,
                    disabledTrailingIconColor = Color.Black
                ),
                trailingIcon = {
                    Icon(
                        Icons.Default.CalendarToday,
                        contentDescription = null,
                    )
                }
            )

            DefaultOutlinedTextField(
                value = idCode,
                onValueChange = { idCode = it },
                label = { Text("Ідентифікаційний код") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )

            PhoneNumberTextField(phoneNumber) { newPhoneNumber ->
                phoneNumber = newPhoneNumber
            }

            GenderSelector(
                gender = gender,
                modifier = Modifier.width(IntrinsicSize.Max).align(Alignment.Start),
                onGenderSelected = { gender = it })

//            EmailInput(
//                email = email,
//                onValueChange = {
//                    email = it
//                    emailError = !isValidEmail(email)
//                },
//                labelText = "Електронна адреса",
//                isError = emailError
//            )

            DefaultOutlinedTextField(
                value = passport,
                onValueChange = { passport = it },
                label = { Text("Серія та номер паспорта") },
                placeholder = { PlaceholderText("AA123456") }
            )

            Button(
                onClick = {
                    if (fullName.isNotBlank() && dateOfBirth != null && idCode.isNotBlank()
                        && phoneNumber.isNotBlank() && gender.isNotBlank() && !emailError && passport.isNotBlank()
                    ) {
                        onNext()
                    }
                },
                modifier = Modifier.align(Alignment.End),
                enabled = fullName.isNotBlank() && dateOfBirth != null && idCode.isNotBlank()
                        && phoneNumber.isNotBlank() && gender.isNotBlank() && !emailError && passport.isNotBlank()
            ) {
                Text("Далі")
            }
        }
    }

    if (showDatePicker) {
        WheelDatePickerBottomSheet(
            onDoneClick = {newDateOfBirth ->
                dateOfBirth = newDateOfBirth
                showDatePicker = false
            },
        ) {
            showDatePicker = false
        }
    }
}
