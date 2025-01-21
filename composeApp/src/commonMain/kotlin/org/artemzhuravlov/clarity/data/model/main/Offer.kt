package org.artemzhuravlov.clarity.data.model.main

import kotlinx.serialization.Serializable

@Serializable
data class Offer(
    val imageUrl: String,
    val title: String,
    val content: String,
    val date: String
)
