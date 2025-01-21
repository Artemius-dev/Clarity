package org.artemzhuravlov.clarity.navigation.main

import org.artemzhuravlov.clarity.data.model.main.Notification
import org.artemzhuravlov.clarity.data.model.main.Offer

data class MainScreenState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val data: List<String> = emptyList(),
    val notifications: List<Notification> = emptyList(),
    val offers: List<Offer> = emptyList(),
)
