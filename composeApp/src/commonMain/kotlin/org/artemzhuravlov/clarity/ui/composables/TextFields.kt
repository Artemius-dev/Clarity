package org.artemzhuravlov.clarity.ui.composables

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation.Companion.keyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.artemzhuravlov.clarity.ui.utils.NanpVisualTransformation

@Composable
fun DefaultOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource? = null,
    shape: Shape = OutlinedTextFieldDefaults.shape,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors()
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.sizeIn(maxWidth = 512.dp)
            .fillMaxWidth(),
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        label = label,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        prefix = prefix,
        suffix = suffix,
        supportingText = supportingText,
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors
    )
}

@Composable
fun EmailInput(
    email: State<String>,
    modifier: Modifier = Modifier,
    labelText: String,
    labelColor: Color = Black,
    placeholderColor: Color = Black.copy(alpha = 0.5f),
    shape: Shape = RoundedCornerShape(8.dp),
    isError: Boolean = false, // Add error state
    errorMessage: String? = null, // Add error message
    onValueChange: (String) -> Unit,
) {
    DefaultOutlinedTextField(
        value = email.value,
        onValueChange = onValueChange,
        label = {
            Text(text = labelText, color = labelColor)
        },
        modifier = modifier
            .padding(vertical = 4.dp),
        shape = shape,
        trailingIcon = {
            androidx.compose.material3.Icon(Icons.Filled.Email, contentDescription = "Email icon")
        },
        placeholder = {
            Text(text = "john.doe@mail.com", color = placeholderColor)
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        isError = isError,
        supportingText = {
            if (isError) {
                Text(text = errorMessage ?: "Invalid email format", color = Color.Red)
            }
        },
    )
}


@Composable
fun PasswordInput(
    password: State<String>,
    isPasswordVisible: Boolean,
    onPasswordVisibilityToggle: () -> Unit,
    modifier: Modifier = Modifier,
    labelText: String,
    labelColor: Color = Black,
    placeholderColor: Color = Color.Black.copy(alpha = 0.5f),
    iconTint: Color = Black,
    shape: Shape = RoundedCornerShape(8.dp),
    isError: Boolean = false, // Add error state
    errorMessage: String? = null, // Add error message
    onPasswordChange: (String) -> Unit,
    ) {

    DefaultOutlinedTextField(
        value = password.value,
        onValueChange = onPasswordChange,
        label = {
            Text(text = labelText, color = labelColor)
        },
        modifier = modifier
            .padding(vertical = 4.dp),
        shape = shape,
        trailingIcon = {
            val image = if (isPasswordVisible)
                Icons.Filled.VisibilityOff
            else Icons.Filled.Visibility

            val description = if (isPasswordVisible) "Hide password" else "Show password"

            IconButton(onClick = onPasswordVisibilityToggle) {
                androidx.compose.material3.Icon(
                    imageVector = image,
                    contentDescription = description,
                )
            }
        },
        placeholder = {
            Text(text = "", color = placeholderColor)
        },
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        isError = isError,
        supportingText = {
            if (isError) {
                Text(text = errorMessage ?: "Invalid password format", color = Color.Red)
            }
        },
    )
//    Column(
//        modifier = Modifier
//            .wrapContentSize(),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(
//            modifier = Modifier.sizeIn(maxWidth = 800.dp)
//                .fillMaxWidth(),
//            text = labelText,
//            textAlign = TextAlign.Start,
//            color = labelColor,
//        )
//        TextField(
//            value = password.value,
//            onValueChange = onPasswordChange,
//            modifier = modifier
//                .sizeIn(maxWidth = 800.dp)
//                .fillMaxWidth()
//                .padding(vertical = 4.dp),
//            singleLine = true,
//            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
//            shape = shape,
//            placeholder = {
//                androidx.compose.material3.Text(
//                    text = "Enter your password",
//                    color = placeholderColor
//                )
//            },
//            isError = isError,
//            supportingText = {
//                if (isError) {
//                    androidx.compose.material3.Text(
//                        text = errorMessage ?: "Invalid password format",
//                        color = Color.Red
//                    )
//                }
//            },
//            colors = TextFieldDefaults.colors().copy(
//                focusedIndicatorColor = Color.Transparent,
//                unfocusedIndicatorColor = Color.Transparent,
//                disabledIndicatorColor = Color.Transparent,
//            )
//        )
//    }
}

@Composable
fun PhoneNumberTextField(phoneNumber: String, onValueChange: (String) -> Unit) {
    val numericRegex = Regex("[^0-9]")

    DefaultOutlinedTextField(
        value = phoneNumber,
        onValueChange = {
            val stripped = numericRegex.replace(it, "")
            onValueChange(
                if (stripped.length >= 9) {
                    stripped.substring(0..8)
                } else {
                    stripped
                }
            )
        },
        prefix = {
            Text("+380")
        },
        label = { Text("Контактний номер телефону") },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
        visualTransformation = NanpVisualTransformation()
    )
}
