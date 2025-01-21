package org.artemzhuravlov.clarity.data.model.main

import kotlinx.serialization.Serializable

@Serializable
data class Notification(
    val title: String,
    val date: String
)
