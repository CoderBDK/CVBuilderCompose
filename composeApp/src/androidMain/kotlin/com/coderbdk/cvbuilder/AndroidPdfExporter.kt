package com.coderbdk.cvbuilder

import android.content.Context
import android.graphics.pdf.PdfDocument
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.coderbdk.cvbuilder.data.model.CVTemplate
import com.coderbdk.cvbuilder.pdf.PdfCanvas
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AndroidPdfExporter(private val context: Context) : PdfExporter {

    private var exportContinuation: Continuation<Unit>? = null
    private var exportLauncher: ActivityResultLauncher<String>? = null
    private var pendingPdfDocument: PdfDocument? = null

    fun register(activity: ComponentActivity) {
        exportLauncher =
            activity.registerForActivityResult(ActivityResultContracts.CreateDocument("application/pdf")) { uri ->
                if (uri != null) {
                    context.contentResolver.openOutputStream(uri)?.use { output ->
                        val pdfDocument = pendingPdfDocument ?: return@use
                        pdfDocument.writeTo(output)
                    }
                    exportContinuation?.resume(Unit)
                } else {
                    exportContinuation?.resumeWith(Result.failure(Exception("Export cancelled")))
                }
                exportContinuation = null
                pendingPdfDocument = null
            }
    }

    override suspend fun export(
        template: CVTemplate,
        onPageRequest: (Int) -> Unit,
        onPageRender: suspend (Int, PdfCanvas) -> Unit
    ) {
        val pageWidth = template.pageWidth
        val pageHeight = template.pageHeight


        val pdfDocument = PdfDocument()

        template.pages.forEachIndexed { pageIndex, cvPage ->


            val pageInfo =
                PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageIndex + 1).create()
            val page = pdfDocument.startPage(pageInfo)

            onPageRequest(pageIndex)
            onPageRender(pageIndex, AndroidPdfCanvas(page.canvas))
            pdfDocument.finishPage(page)


        }
        pendingPdfDocument = pdfDocument

        return suspendCoroutine { continuation ->
            exportContinuation = continuation
            exportLauncher?.launch("test_demo.pdf")
        }
    }

}