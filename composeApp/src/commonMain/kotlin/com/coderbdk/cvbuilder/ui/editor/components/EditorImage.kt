package com.coderbdk.cvbuilder.ui.editor.components

import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import org.jetbrains.compose.resources.decodeToImageBitmap
import kotlin.io.encoding.Base64


@Composable
fun EditorImage(
    data: String?,
    contentScale: ContentScale = ContentScale.Fit,
    modifier: Modifier = Modifier,
) {
    if (data == null) {
        Image(
            imageVector = Icons.Default.Error,
            contentDescription = "error",
            modifier = modifier
        )
        return
    }

    EditorBase64Image(
        data = data,
        contentDescription = "image",
        contentScale = contentScale,
        modifier = modifier
    )
}

fun String.base64ToImageBitmap(): ImageBitmap? {
    return try {
        val base64Data = substringAfter(",")
        val bytes = Base64.decode(base64Data)
        bytes.decodeToImageBitmap()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

@Composable
private fun EditorBase64Image(
    data: String?,
    contentScale: ContentScale,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
) {
    if (data == null) {
        Image(
            imageVector = Icons.Default.Error,
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = modifier
        )
        return
    }

    val bitmap = remember(data) { data.base64ToImageBitmap() }

    if (bitmap != null) {
        Image(
            bitmap = bitmap,
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = modifier
        )
    } else {
        Image(
            imageVector = Icons.Default.Error,
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = modifier
        )
    }
}
