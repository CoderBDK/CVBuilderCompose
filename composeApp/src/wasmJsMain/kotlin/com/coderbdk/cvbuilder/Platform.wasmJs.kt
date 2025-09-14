package com.coderbdk.cvbuilder

class WasmPlatform: Platform {
    override val name: String = "Web with Kotlin/Wasm"
    override val pdfExporter: PdfExporter = WebPdfExporter()
    override val cvFileHandler: CVFileHandler = WebCVFileHandler()
    override val imagePicker: ImagePicker = WebImagePicker()
}

actual fun getPlatform(): Platform = WasmPlatform()