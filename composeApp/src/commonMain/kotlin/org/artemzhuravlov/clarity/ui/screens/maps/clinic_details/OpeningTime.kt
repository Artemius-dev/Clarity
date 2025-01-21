package org.artemzhuravlov.clarity.ui.screens.maps.clinic_details

import kotlinx.datetime.DayOfWeek

data class OpeningTime(
    val day: DayOfWeek,
    val hours: String
)
