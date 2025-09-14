package com.coderbdk.cvbuilder

import com.coderbdk.cvbuilder.data.model.CVTemplate
import com.coderbdk.cvbuilder.pdf.PdfCanvas
import kotlinx.coroutines.delay
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.common.PDRectangle
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DesktopPdfExporter : PdfExporter {
    // private val scope = CoroutineScope(Dispatchers.IO)

    val sdf = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())

    override suspend fun export(
        template: CVTemplate,
        onPageRequest: (Int) -> Unit,
        onPageRender: suspend (Int, PdfCanvas) -> Unit
    ) {
        val home = System.getProperty("user.home")
        val timestamp = sdf.format(Date())
        val exportFilePath = "$home/pdftest/test_demo_$timestamp.pdf"

        PDDocument().use { doc ->
            template.pages.forEachIndexed { index, cvPage ->
                val page = PDPage(PDRectangle.A4)
                doc.addPage(page)

                onPageRequest(index)
                PDPageContentStream(doc, page).use { content ->
                    onPageRender(index, DesktopPdfCanvas( doc, content))
                }
            }
            doc.save(File(exportFilePath))
        }
    }


}
