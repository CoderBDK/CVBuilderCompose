package com.coderbdk.cvbuilder.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavHostController

class CVNavActions(private val navController: NavHostController) {
    fun navigateToEditor(data: String){
        navController.navigate(Screen.Editor(data))
    }

    fun navigateUp() {
        navController.navigateUp()
    }
}