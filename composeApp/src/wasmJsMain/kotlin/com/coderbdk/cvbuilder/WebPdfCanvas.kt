package com.coderbdk.cvbuilder

import androidx.compose.ui.graphics.ImageBitmap
import com.coderbdk.cvbuilder.pdf.PdfCanvas
import com.coderbdk.cvbuilder.util.addImage
import com.coderbdk.cvbuilder.util.toBase64EncodedDataUrl

class WebPdfCanvas(
    private val doc: JsAny
) : PdfCanvas {
    override fun drawRect(x: Float, y: Float, width: Float, height: Float, color: String) {
        TODO("Not yet implemented")
    }

    override fun drawText(text: String, x: Float, y: Float, fontSize: Float) {
        TODO("Not yet implemented")
    }

    override fun drawImage(x: Float, y: Float, image: ImageBitmap) {
        TODO("Not yet implemented")
    }

    override fun renderPage(bitmap: ImageBitmap) {
        bitmap.toBase64EncodedDataUrl()
            ?.let { addImage(doc, it, 0f, 0f, bitmap.width.toFloat(), bitmap.height.toFloat()) }
    }
}