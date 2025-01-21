package org.artemzhuravlov.clarity.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler
import org.artemzhuravlov.clarity.data.model.place.LatLng

@Composable
fun openExternalWebPage(url: String) {
    val uriHandler = LocalUriHandler.current
    uriHandler.openUri(url)
}

@Composable
expect fun makePhoneCall(phoneNumber: String)

@Composable
expect fun sendEmail(emailAddress: String, subject: String = "", body: String = "")

@Composable
expect fun openTelegramChat()

@Composable
expect fun openViberChat()

@Composable
expect fun openGoogleMaps(latLng: LatLng, label: String? = null)

@Composable
expect fun openPdf(pdfUrl: String)
