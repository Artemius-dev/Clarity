package org.artemzhuravlov.clarity.ui.utils

import androidx.compose.runtime.Composable
import org.artemzhuravlov.clarity.data.model.place.LatLng

@Composable
actual fun makePhoneCall(phoneNumber: String) {

}

@Composable
actual fun sendEmail(emailAddress: String, subject: String, body: String) {

}

@Composable
actual fun openTelegramChat() {
}

@Composable
actual fun openViberChat() {
}

@Composable
actual fun openGoogleMaps(
    latLng: LatLng,
    label: String?
) {
}

@Composable
actual fun openPdf(pdfUrl: String) {
}