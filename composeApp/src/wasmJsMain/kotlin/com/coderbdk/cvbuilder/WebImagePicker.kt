package com.coderbdk.cvbuilder

import kotlinx.browser.document
import kotlinx.coroutines.CompletableDeferred
import org.w3c.dom.HTMLInputElement
import org.w3c.files.FileReader

class WebImagePicker : ImagePicker {
    override suspend fun pickImage(): String? {
        val deferred = CompletableDeferred<String?>()
        val input = document.createElement("input") as HTMLInputElement
        input.type = "file"
        input.accept = "image/*"

        input.onchange = {
            val file = input.files?.item(0)
            if (file != null) {
                val reader = FileReader()
                reader.onload = {
                    val result = reader.result?.toString()
                    deferred.complete(result)
                }
                reader.onerror = {
                    deferred.complete(null)
                }
                reader.readAsDataURL(file)
            } else {
                deferred.complete(null)
            }
        }

        input.click()
        return deferred.await()
    }
}
