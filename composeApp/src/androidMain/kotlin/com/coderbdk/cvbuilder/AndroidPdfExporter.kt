package com.coderbdk.cvbuilder

import android.content.Context
import android.graphics.pdf.PdfDocument
import com.coderbdk.cvbuilder.data.model.CVTemplate
import com.coderbdk.cvbuilder.pdf.PdfCanvas
import java.io.File
import java.io.FileOutputStream

class AndroidPdfExporter(private val context: Context) : PdfExporter {

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

        val file = File(context.filesDir, "test_demo.pdf")
        pdfDocument.writeTo(FileOutputStream(file))
        pdfDocument.close()
    }

}