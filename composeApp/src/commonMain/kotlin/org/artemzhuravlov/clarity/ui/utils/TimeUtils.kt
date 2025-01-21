package org.artemzhuravlov.clarity.ui.utils

import dev.gitlive.firebase.firestore.Timestamp
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun convertFirebaseTimestampToDate(timestamp: Timestamp): String {
    // Convert seconds to an Instant
    val instant = Instant.fromEpochSeconds(timestamp.seconds)

    // Convert to local date-time in the desired timezone
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())

    // Format as a string (you can customize the output)
    return "${localDateTime.year}-${localDateTime.monthNumber}-${localDateTime.dayOfMonth}"
}
