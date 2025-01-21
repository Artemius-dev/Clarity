package org.artemzhuravlov.clarity.ui.screens.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import org.artemzhuravlov.clarity.navigation.login.LoginScreenEvent
import org.artemzhuravlov.clarity.navigation.login.ILoginScreenComponent
import org.artemzhuravlov.clarity.ui.composables.DefaultButton
import org.artemzhuravlov.clarity.ui.composables.EmailInput
import org.artemzhuravlov.clarity.ui.composables.PasswordInput

@Composable
fun LoginScreen(modifier: Modifier = Modifier, component: ILoginScreenComponent) {
    val email = component.email.subscribeAsState()
    val password = component.password.subscribeAsState()
    var isPasswordVisible by remember { mutableStateOf(false) }
    val emailError = component.validationEmailError.subscribeAsState()
    val passwordError = component.validationPasswordError.subscribeAsState()
    Column(
        modifier = modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(modifier = Modifier.wrapContentSize(), horizontalArrangement = Arrangement.Center) {
            Column(horizontalAlignment = Alignment.Start) {
                Text("Зайти в аккаунт", fontSize = 32.sp, fontWeight = FontWeight.Bold)
                Text("Введіть дані для входу в систему")
                Spacer(modifier = Modifier.height(32.dp))
                EmailInput(
                    email = email,
                    labelText = "Пошта",
                    isError = emailError.value.isNotBlank(),
                    errorMessage = emailError.value
                ) {
                    component.onEvent(LoginScreenEvent.UpdateEmail(it))
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(modifier = Modifier.wrapContentSize(), horizontalArrangement = Arrangement.Center) {
            Column(modifier = Modifier.wrapContentSize(), horizontalAlignment = Alignment.End) {
                PasswordInput(
                    password = password,
                    isPasswordVisible = isPasswordVisible,
                    onPasswordVisibilityToggle = { isPasswordVisible = !isPasswordVisible },
                    isError = passwordError.value.isNotBlank(),
                    errorMessage = passwordError.value,
                    labelText = "Пароль"
                ) {
                    component.onEvent(LoginScreenEvent.UpdatePassword(it))
                }

                Text(
                    text = "Забули пароль?",
                    modifier = Modifier.padding(8.dp).clickable {

                    },
                    textAlign = TextAlign.End,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        }


        Spacer(modifier = Modifier.height(24.dp))

        DefaultButton(
            enabled = if (emailError.value.isBlank()
                && passwordError.value.isBlank()
                && email.value.isNotBlank()
                && password.value.isNotBlank()) true else false,
            onClick = {
                component.onEvent(LoginScreenEvent.ClickLogin)
            }) {
            Text(text = "Вхід".toUpperCase(locale = Locale.current))
        }
    }
}
