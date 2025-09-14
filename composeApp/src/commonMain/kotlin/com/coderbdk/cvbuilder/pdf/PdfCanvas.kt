package com.coderbdk.cvbuilder.pdf

import androidx.compose.ui.graphics.ImageBitmap

interface PdfCanvas {
    fun drawRect(x: Float, y: Float, width: Float, height: Float, color: String)
    fun drawText(text: String, x: Float, y: Float, fontSize: Float)
    fun drawImage(x: Float, y: Float, image: ImageBitmap)
    fun renderPage(bitmap: ImageBitmap)
}
