package com.coderbdk.cvbuilder

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.swing.JFileChooser
import kotlin.io.encoding.Base64

class DesktopImagePicker : ImagePicker {
    override suspend fun pickImage(): String? {
        val fileChooser = JFileChooser()
        fileChooser.dialogTitle = "Select Image file"
        fileChooser.fileSelectionMode = JFileChooser.FILES_ONLY

        val result = withContext(Dispatchers.Main) {
            fileChooser.showOpenDialog(null)
        }

        if (result == JFileChooser.APPROVE_OPTION) {
            val selectedFile = fileChooser.selectedFile
            if (selectedFile.extension.lowercase() in listOf("png", "jpg", "jpeg")) {
                val bytes = selectedFile.readBytes()
                val base64 = Base64.encode(bytes)
                val mime = when (selectedFile.extension.lowercase()) {
                    "png" -> "image/png"
                    "jpg", "jpeg" -> "image/jpeg"
                    else -> "application/octet-stream"
                }
                return "data:$mime;base64,$base64"
            }
        }
        return null
    }
}
