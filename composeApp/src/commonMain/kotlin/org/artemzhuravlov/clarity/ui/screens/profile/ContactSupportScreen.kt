package org.artemzhuravlov.clarity.ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType.Companion.Uri
import androidx.compose.ui.unit.dp
import org.artemzhuravlov.clarity.ui.utils.makePhoneCall
import org.artemzhuravlov.clarity.ui.utils.sendEmail

@Composable
fun ContactSupportScreen(onBack: () -> Unit) {
    var sendEmailTo by remember {
        mutableStateOf("")
    }
    var makePhoneCallTo by remember {
        mutableStateOf("")
    }

    if (sendEmailTo.isNotBlank()) {
        sendEmail(sendEmailTo)
        sendEmailTo = ""
    }

    if (makePhoneCallTo.isNotBlank()) {
        makePhoneCall(makePhoneCallTo)
        makePhoneCallTo = ""
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Контактна підтримка", style = MaterialTheme.typography.h5)

        Button(onClick = { sendEmailTo = "support@example.com" }, modifier = Modifier.fillMaxWidth()) {
            Text("Написати на Email")
        }

        Button(onClick = { makePhoneCallTo = "+380123456789" }, modifier = Modifier.fillMaxWidth()) {
            Text("Зателефонувати")
        }

        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = onBack, modifier = Modifier.align(Alignment.Start)) {
            Text("Назад")
        }
    }
}
