package com.coderbdk.cvbuilder

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.core.graphics.scale
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
        val androidBitmap = bitmap.asAndroidBitmap().copy(
            Bitmap.Config.ARGB_8888, false
        ).scale(canvas.width, canvas.height)
        canvas.drawBitmap(androidBitmap, 0f, 0f, null)
    }
}
