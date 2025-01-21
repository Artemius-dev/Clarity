package org.artemzhuravlov.clarity.navigation.main

sealed interface MainScreenEvent {
    data object DismissErrorDialog : MainScreenEvent
    data object NavigateToContacts : MainScreenEvent
    data object NavigateToInsuredEvent : MainScreenEvent
    data object NavigateToClinicsMap: MainScreenEvent
}
