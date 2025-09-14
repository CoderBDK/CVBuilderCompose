package com.coderbdk.cvbuilder

import com.coderbdk.cvbuilder.data.model.CVTemplate
import com.coderbdk.cvbuilder.pdf.PdfCanvas
import com.coderbdk.cvbuilder.util.addPage
import com.coderbdk.cvbuilder.util.createPdf
import com.coderbdk.cvbuilder.util.outputBlob
import kotlinx.browser.document
import org.w3c.dom.HTMLAnchorElement
import org.w3c.dom.url.URL.Companion.createObjectURL
import org.w3c.dom.url.URL.Companion.revokeObjectURL


class WebPdfExporter : PdfExporter {

    override suspend fun export(
        template: CVTemplate,
        onPageRequest: (Int) -> Unit,
        onPageRender: suspend (Int, PdfCanvas) -> Unit
    ) {

        val pageWidth = template.pageWidth
        val pageHeight = template.pageHeight
        val doc = createPdf(pageWidth, pageHeight)

        template.pages.forEachIndexed { index, page ->
            onPageRequest(index)
            onPageRender(index, WebPdfCanvas(doc))
            if (index < template.pages.lastIndex)
                addPage(doc)
        }

        val pdfBlob = outputBlob(doc)
        val url = createObjectURL(pdfBlob)
        val link = document.createElement("a") as HTMLAnchorElement
        link.href = url
        link.download = "test.pdf"
        link.click()
        revokeObjectURL(url)
    }
}

