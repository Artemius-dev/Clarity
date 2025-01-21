package org.artemzhuravlov.clarity.ui.screens.maps.clinic_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import kotlinx.datetime.LocalDateTime
import localizeToUkrainian
import network.chaintech.kmp_date_time_picker.utils.now

@Composable
fun OpeningTimes(openingTime: List<OpeningTime>) {
    val currentDay = LocalDateTime.now().dayOfWeek

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
    ) {
        items(openingTime) { openingTime ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        if (openingTime.day == currentDay) Color(0xFFE3F2FD) // Highlight color
                        else Color.Transparent
                    )
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = openingTime.day.localizeToUkrainian(),
                    fontSize = 16.sp,
                    fontWeight = if (openingTime.day == currentDay) FontWeight.Bold else FontWeight.Normal,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Start
                )
                Text(
                    text = openingTime.hours,
                    fontSize = 16.sp,
                    fontWeight = if (openingTime.day == currentDay) FontWeight.Bold else FontWeight.Normal,
                    color = if (openingTime.day == currentDay) MaterialTheme.colorScheme.primary else Color.Black,
                    textAlign = TextAlign.End
                )
            }
        }
    }
}
