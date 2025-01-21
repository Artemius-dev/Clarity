package org.artemzhuravlov.clarity.data.model.policies

import dev.gitlive.firebase.firestore.Timestamp
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import org.artemzhuravlov.clarity.ui.utils.convertFirebaseTimestampToDate

@Serializable
data class Policy(
    val policyNumber: String,
    val program: String,
    val patientName: String,
    val medicalId: String,
    val validityPeriodFrom: Timestamp,
    val validityPeriodTo: Timestamp,
) {
    @Transient
    val backgroundColor: Long = when (program) {
        "Premium" -> 0xFF6200EE // Purple
        "Standard" -> 0xFF03DAC5 // Teal
        else -> 0xFFBDBDBD // Default Gray
    }

    @Transient
    val validityPeriod: String = "з ${convertFirebaseTimestampToDate(validityPeriodFrom)} до ${
        convertFirebaseTimestampToDate(validityPeriodTo)
    }"
}
