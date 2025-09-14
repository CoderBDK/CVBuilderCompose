package com.coderbdk.cvbuilder.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object Home : Screen()
    @Serializable
    data class Editor(val templateData: String): Screen()
    @Serializable
    data object Preview: Screen()
}