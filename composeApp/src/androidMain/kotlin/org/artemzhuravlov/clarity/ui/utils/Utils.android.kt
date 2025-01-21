package org.artemzhuravlov.clarity.ui.utils

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import org.artemzhuravlov.clarity.data.model.place.LatLng

@Composable
actual fun makePhoneCall(phoneNumber: String) {
    val context = LocalContext.current
    val intent = Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse("tel:$phoneNumber")
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    context.startActivity(intent)
}

@Composable
actual fun sendEmail(emailAddress: String, subject: String, body: String) {
    val context = LocalContext.current
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:$emailAddress")
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, body)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

    }
    context.startActivity(intent)
}

@Composable
actual fun openTelegramChat() {
    val context = LocalContext.current
    val telegramIntent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse("https://t.me/Artemius_dev")
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    context.startActivity(telegramIntent)
}

@Composable
actual fun openViberChat() {
    val context = LocalContext.current
    val user = "Viber public account uri"
    val intent = Intent(
        Intent.ACTION_VIEW
    ).apply {
        data = Uri.parse(
            "viber://contact?number=%2B0000000000000"
        )
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    context.startActivity(intent)
}

@Composable
actual fun openGoogleMaps(latLng: LatLng, label: String?) {
    val context = LocalContext.current
    val uri =
        Uri.parse("geo:${latLng.latitude},${latLng.longitude}?q=${latLng.latitude},${latLng.longitude}(${label ?: "Точка призначення"})")
    val intent = Intent(Intent.ACTION_VIEW, uri).apply {
        setPackage("com.google.android.apps.maps")
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    try {
        context.startActivity(intent)
    } catch (_: Exception) {

    }
}

@Composable
actual fun openPdf(pdfUrl: String) {
    val context = LocalContext.current
    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse(pdfUrl)
    }
    try {
        context.startActivity(Intent.createChooser(intent, null))
    } catch (_: Exception) {

    }
}