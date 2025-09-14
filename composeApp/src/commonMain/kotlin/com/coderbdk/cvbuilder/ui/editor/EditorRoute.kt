package com.coderbdk.cvbuilder.ui.editor

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun EditorRoute(
    onNavigateUp: () -> Unit
) {
    val viewModel = koinViewModel<EditorViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    EditorScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        onNavigateUp = onNavigateUp
    )
}