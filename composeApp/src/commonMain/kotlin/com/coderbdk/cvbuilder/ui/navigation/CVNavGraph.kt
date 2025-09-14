package com.coderbdk.cvbuilder.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.coderbdk.cvbuilder.ui.editor.EditorRoute
import com.coderbdk.cvbuilder.ui.home.HomeRoute

@Composable
fun CVNavGraph(navController: NavHostController) {

    val navActions = remember { CVNavActions(navController) }

    NavHost(
        navController = navController,
        startDestination = Screen.Home
    ) {
        composable<Screen.Home> {
            HomeRoute(
                onNavigateToEditor = navActions::navigateToEditor
            )
        }
        composable<Screen.Editor> {
            EditorRoute(onNavigateUp = navActions::navigateUp)
        }
    }
}