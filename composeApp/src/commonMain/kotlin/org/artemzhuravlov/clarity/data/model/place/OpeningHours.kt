package org.artemzhuravlov.clarity.data.model.place

import kotlinx.datetime.DayOfWeek
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.artemzhuravlov.clarity.ui.screens.maps.clinic_details.OpeningTime

@Serializable
data class OpeningHours(
    @SerialName("weekday_text")
    val weekdayText: List<String>? = null
) {
    fun openingHoursToOpeningTime(): List<OpeningTime> {
        val openingTime: MutableList<OpeningTime> = mutableListOf()
        this.weekdayText?.forEach { weekday ->
            val weekDay = weekday.split(":", limit = 2)
            val dayOfTheWeek = weekDay[0].uppercase()
            val timeOfTheDay = weekDay[1].trim()
            openingTime.add(OpeningTime(day = DayOfWeek.valueOf(dayOfTheWeek), hours = timeOfTheDay))
        }
        return openingTime.toList()
    }
}