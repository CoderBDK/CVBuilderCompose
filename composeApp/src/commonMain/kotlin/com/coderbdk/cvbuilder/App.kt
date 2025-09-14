package com.coderbdk.cvbuilder

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.coderbdk.cvbuilder.di.initKoin
import com.coderbdk.cvbuilder.ui.navigation.CVNavGraph
import com.coderbdk.cvbuilder.ui.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val navController = rememberNavController()
    AppTheme(darkTheme = false) {
        Surface {
            CVNavGraph(navController)
        }
    }
}