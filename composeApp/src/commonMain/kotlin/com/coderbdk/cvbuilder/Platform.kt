package com.coderbdk.cvbuilder

import com.coderbdk.cvbuilder.data.model.CVTemplate
import com.coderbdk.cvbuilder.pdf.PdfCanvas

interface Platform {
    val name: String
    val pdfExporter: PdfExporter
    val cvFileHandler: CVFileHandler
    val imagePicker: ImagePicker
}

interface PdfExporter {
    suspend fun export(
        template: CVTemplate,
        onPageRequest: (pageIndex: Int) -> Unit,
        onPageRender: suspend (pageIndex: Int, pdfCanvas: PdfCanvas) -> Unit
    )
}

interface CVFileHandler {
    suspend fun export(template: CVTemplate)
    suspend fun import(): CVTemplate?
}

interface ImagePicker {
    suspend fun pickImage(): String?
}

expect fun getPlatform(): Platform