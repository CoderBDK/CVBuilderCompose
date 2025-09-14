package com.coderbdk.cvbuilder

class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
    override val pdfExporter: PdfExporter = DesktopPdfExporter()
    override val cvFileHandler: CVFileHandler = DesktopCVFileHandler()
    override val imagePicker: ImagePicker = DesktopImagePicker()
}

actual fun getPlatform(): Platform = JVMPlatform()