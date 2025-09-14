package com.coderbdk.cvbuilder

import android.os.Build
import com.coderbdk.cvbuilder.CVApplication.Companion.AppContext

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
    override val pdfExporter: PdfExporter = AndroidPdfExporter(AppContext)
    override val cvFileHandler: CVFileHandler = AndroidCVFileHandler()
    override val imagePicker: ImagePicker = AndroidImagePicker()
}

actual fun getPlatform(): Platform = AndroidPlatform()