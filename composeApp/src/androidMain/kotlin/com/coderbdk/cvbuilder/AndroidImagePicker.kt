package com.coderbdk.cvbuilder

import android.content.Context
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import kotlin.coroutines.Continuation
import kotlin.coroutines.suspendCoroutine
import kotlin.io.encoding.Base64

class AndroidImagePicker(
    private val context: Context
) : ImagePicker {

    private var pickedImageContinuation: Continuation<String?>? = null
    private var imagePickerLauncher: ActivityResultLauncher<String>? = null

    fun register(activity: ComponentActivity) {
        imagePickerLauncher = activity.registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            uri?.let {
                pickedImageContinuation?.resumeWith(Result.success(encodeImageToBase64(it)))
            } ?: pickedImageContinuation?.resumeWith(Result.success(null))
        }
    }

    override suspend fun pickImage(): String? =
        suspendCoroutine { continuation ->
            pickedImageContinuation = continuation
            imagePickerLauncher?.launch("image/*")
        }

    private fun encodeImageToBase64(uri: Uri): String? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val bytes = inputStream?.readBytes()
            inputStream?.close()
            bytes?.let {
                Base64.encode(it)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
