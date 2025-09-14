package com.coderbdk.cvbuilder

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.coderbdk.cvbuilder.di.initKoin
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    initKoin()
    ComposeViewport(document.body!!) {
        App()
        removeLoadingOverlay()
    }
}
fun removeLoadingOverlay() {
    val el = document.getElementById("loading")
    el?.remove()
}