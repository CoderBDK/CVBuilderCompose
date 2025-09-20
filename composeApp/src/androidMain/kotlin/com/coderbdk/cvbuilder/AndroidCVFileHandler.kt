package com.coderbdk.cvbuilder

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.coderbdk.cvbuilder.data.model.CVTemplate
import com.coderbdk.cvbuilder.util.toCVTemplate
import com.coderbdk.cvbuilder.util.toJsonString
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AndroidCVFileHandler(
    private val context: Context,
) : CVFileHandler {

    private var exportContinuation: Continuation<Unit>? = null
    private var importContinuation: Continuation<CVTemplate?>? = null

    private var exportLauncher: ActivityResultLauncher<String>? = null
    private var importLauncher: ActivityResultLauncher<Array<String>>? = null

    private var pendingTemplate: CVTemplate? = null

    fun register(activity: ComponentActivity) {
        exportLauncher =
            activity.registerForActivityResult(ActivityResultContracts.CreateDocument("application/json")) { uri ->
                if (uri != null) {
                    context.contentResolver.openOutputStream(uri)?.use { output ->
                        val template = pendingTemplate ?: return@use
                        output.write(template.toJsonString().toByteArray())
                    }
                    exportContinuation?.resume(Unit)
                } else {
                    exportContinuation?.resumeWith(Result.failure(Exception("Export cancelled")))
                }
                exportContinuation = null
                pendingTemplate = null
            }

        importLauncher =
            activity.registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
                if (uri != null) {
                    context.contentResolver.openInputStream(uri)?.use { input ->
                        val content = input.bufferedReader().readText()
                        val template = content.toCVTemplate()
                        importContinuation?.resume(template)
                    }
                } else {
                    importContinuation?.resume(null)
                }
                importContinuation = null
            }
    }

    override suspend fun export(template: CVTemplate) {
        return suspendCoroutine { continuation ->
            exportContinuation = continuation
            pendingTemplate = template
            exportLauncher?.launch("cv_template.json")
                ?: continuation.resumeWith(Result.failure(IllegalStateException("Launcher not registered")))
        }
    }

    override suspend fun import(): CVTemplate? {
        return suspendCoroutine { continuation ->
            importContinuation = continuation
            importLauncher?.launch(arrayOf("application/json"))
                ?: continuation.resumeWith(Result.failure(IllegalStateException("Launcher not registered")))
        }
    }
}
