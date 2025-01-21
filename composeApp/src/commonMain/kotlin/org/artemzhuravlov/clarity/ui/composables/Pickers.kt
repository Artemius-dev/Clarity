package org.artemzhuravlov.clarity.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import network.chaintech.kmp_date_time_picker.ui.datepicker.WheelDatePickerView
import network.chaintech.kmp_date_time_picker.utils.DateTimePickerView
import network.chaintech.kmp_date_time_picker.utils.WheelPickerDefaults
import network.chaintech.kmp_date_time_picker.utils.now

@Composable
fun WheelDatePickerBottomSheet(onDoneClick: (LocalDate) -> Unit, onDismissClick: () -> Unit) {
    WheelDatePickerView(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 22.dp, bottom = 26.dp),
        showDatePicker = true,
        title = "Оберіть свою дату народження",
        doneLabel = "Готово",
        startDate = LocalDate.now().minus(value = 18, DateTimeUnit.YEAR),
        yearsRange = IntRange(1922, LocalDate.now().minus(value = 18, DateTimeUnit.YEAR).year) ,
        titleStyle = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF333333),
        ),
        doneLabelStyle = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight(600),
            color = Color(0xFF007AFF),
        ),
        dateTextColor = Color(0xff007AFF),
        selectorProperties = WheelPickerDefaults.selectorProperties(
            borderColor = Color.LightGray,
        ),
        rowCount = 5,
        height = 180.dp,
        dateTextStyle = TextStyle(
            fontWeight = FontWeight(600),
        ),
        dragHandle = {
            HorizontalDivider(
                modifier = Modifier.padding(top = 8.dp).width(50.dp).clip(CircleShape),
                thickness = 4.dp,
                color = Color(0xFFE8E4E4)
            )
        },
        shape = RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp),
        dateTimePickerView = DateTimePickerView.BOTTOM_SHEET_VIEW,
        onDoneClick = {
            onDoneClick(it)
        },
        onDismiss = {
            onDismissClick()
        }
    )
}

@Composable
fun GenderSelector(gender: String, modifier: Modifier = Modifier,  onGenderSelected: (String) -> Unit) {
    val options = listOf("Чоловік", "Жінка", "Інше")

    Column(modifier = modifier) {
        Text("Стать", style = MaterialTheme.typography.body1)
        options.forEach { option ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = gender == option,
                    onClick = { onGenderSelected(option) }
                )
                Text(option, modifier = Modifier.clickable { onGenderSelected(option) })
            }
        }
    }
}
