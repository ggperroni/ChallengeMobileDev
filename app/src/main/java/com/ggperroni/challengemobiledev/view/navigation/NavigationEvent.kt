package com.ggperroni.challengemobiledev.view.navigation

sealed class NavigationEvent {
    data class Navigate(val route: String) : NavigationEvent()
}