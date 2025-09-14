package com.coderbdk.cvbuilder

import android.graphics.Canvas
import android.graphics.Paint
import androidx.compose.ui.graphics.ImageBitmap
import androidx.core.graphics.toColorInt
import com.coderbdk.cvbuilder.pdf.PdfCanvas

class AndroidPdfCanvas(private val canvas: Canvas) : PdfCanvas {
    private val paint = Paint()

    override fun drawRect(x: Float, y: Float, width: Float, height: Float, color: String) {
        paint.color = color.toColorInt()
        paint.style = Paint.Style.FILL
        canvas.drawRect(x, y, x + width, y + height, paint)
    }

    override fun drawImage(x: Float, y: Float, image: ImageBitmap) {
        TODO("Not yet implemented")
    }
    override fun drawText(text: String, x: Float, y: Float, fontSize: Float) {
        TODO("Not yet implemented")
    }
    override fun renderPage(bitmap: ImageBitmap) {
        TODO("Not yet implemented")
    }
}
