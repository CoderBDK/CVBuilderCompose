package com.coderbdk.cvbuilder

import com.coderbdk.cvbuilder.data.model.CVTemplate
import com.coderbdk.cvbuilder.util.toCVTemplate
import com.coderbdk.cvbuilder.util.toJsonString
import com.coderbdk.cvbuilder.util.stringToJsArray

import org.w3c.dom.HTMLInputElement
import kotlinx.browser.document
import kotlinx.coroutines.CompletableDeferred
import org.w3c.dom.HTMLAnchorElement
import org.w3c.dom.url.URL.Companion.createObjectURL
import org.w3c.dom.url.URL.Companion.revokeObjectURL
import org.w3c.files.Blob
import org.w3c.files.BlobPropertyBag
import org.w3c.files.FileReader


class WebCVFileHandler : CVFileHandler {
    override suspend fun export(template: CVTemplate) {
        val blob =
            Blob(stringToJsArray(template.toJsonString()), BlobPropertyBag("application/json"))
        val url = createObjectURL(blob)
        val link = document.createElement("a") as HTMLAnchorElement
        link.href = url
        link.download = "cv_data.json"
        link.click()
        revokeObjectURL(url)
    }
    override suspend fun import(): CVTemplate? {
        val deferred = CompletableDeferred<String?>()
        val input = document.createElement("input") as HTMLInputElement
        input.type = "file"
        input.accept = ".json"

        input.onchange = {
            val file = input.files?.item(0)
            if (file != null) {
                val reader = FileReader()
                reader.onload = {
                    val content = reader.result?.toString()
                    deferred.complete(content)
                }
                reader.onerror = {
                    deferred.complete(null)
                    println("Cancel")
                }
                reader.readAsText(file)
            } else {
                deferred.complete(null)
            }
        }

        input.oncancel = {
            deferred.complete(null)
        }

        input.click()


        return deferred.await()?.toCVTemplate()
    }


}
