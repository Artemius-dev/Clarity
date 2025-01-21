package org.artemzhuravlov.clarity.ui.composables

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.Placeholder

@Composable
fun PlaceholderText(text: String, color: Color = Color.LightGray, modifier: Modifier = Modifier) {
    Text(text = text, color = color, modifier = modifier)
}