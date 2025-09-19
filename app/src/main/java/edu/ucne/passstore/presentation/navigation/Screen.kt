package edu.ucne.passstore.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object HomeScreen: Screen()

    @Serializable
    data object SubcuentaScreen: Screen()

    @Serializable
    data object SettingScreen: Screen()
}