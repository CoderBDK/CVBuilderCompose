package com.coderbdk.cvbuilder.util

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asSkiaBitmap
import org.jetbrains.skia.Image
import kotlin.io.encoding.Base64

fun stringToJsArray(content: String): JsArray<JsAny?> = js("[content]")

fun ImageBitmap.toBase64EncodedDataUrl(): String? {
    return try {
        val skiaImage = Image.makeFromBitmap(this.asSkiaBitmap())
        val bytes = skiaImage.encodeToData()?.bytes ?: return null
        Base64.encode(bytes)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
