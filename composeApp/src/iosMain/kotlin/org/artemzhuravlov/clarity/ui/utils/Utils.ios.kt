package org.artemzhuravlov.clarity.ui.utils

import androidx.compose.runtime.Composable
import org.artemzhuravlov.clarity.data.model.place.LatLng
import platform.Foundation.NSURL
import platform.UIKit.UIApplication

@Composable
actual fun makePhoneCall(phoneNumber: String) {
    val url = NSURL.URLWithString("tel:$phoneNumber")
    if (url != null) {
        UIApplication.sharedApplication.openURL(url)
    }
}

@Composable
actual fun sendEmail(emailAddress: String, subject: String, body: String) {
    val encodedSubject = subject.replace(" ", "%20")
    val encodedBody = body.replace(" ", "%20")
    val url = NSURL.URLWithString("mailto:$emailAddress?subject=$encodedSubject&body=$encodedBody")
    if (url != null) {
        UIApplication.sharedApplication.openURL(url)
    }
}

@Composable
actual fun openTelegramChat() {
    val telegramUrl = NSURL(string = "https://t.me/Artemius_dev")
    if (telegramUrl != null && UIApplication.sharedApplication.canOpenURL(telegramUrl)) {
        UIApplication.sharedApplication.openURL(telegramUrl)
    }
}

@Composable
actual fun openViberChat() {
    val telegramUrl = NSURL(string = "viber://contact?number=%2B0000000000000")
    if (telegramUrl != null && UIApplication.sharedApplication.canOpenURL(telegramUrl)) {
        UIApplication.sharedApplication.openURL(telegramUrl)
    }
}

@Composable
actual fun openGoogleMaps(latLng: LatLng, label: String?) {
    val url =
        "comgooglemaps://?q=${latLng.latitude},${latLng.longitude}&center=${latLng.latitude},${latLng.longitude}"
    val nsUrl = NSURL(string = url) ?: return

    if (UIApplication.sharedApplication.canOpenURL(nsUrl)) {
        UIApplication.sharedApplication.openURL(nsUrl)
    } else {
        // Fallback to Apple Maps if Google Maps is not installed
        val appleMapsUrl = "http://maps.apple.com/?q=${latLng.latitude},${latLng.longitude}"
        UIApplication.sharedApplication.openURL(NSURL(string = appleMapsUrl) ?: return)
    }
}

@Composable
actual fun openPdf(pdfUrl: String) {
    val url = NSURL.URLWithString(pdfUrl)!!
    if (UIApplication.sharedApplication.canOpenURL(url)) {
        UIApplication.sharedApplication.openURL(url)
    } else {
        println("No app available to open the PDF")
    }
}
