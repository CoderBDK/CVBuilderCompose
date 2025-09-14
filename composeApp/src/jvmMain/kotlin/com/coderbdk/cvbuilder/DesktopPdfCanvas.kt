package com.coderbdk.cvbuilder

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toAwtImage
import com.coderbdk.cvbuilder.pdf.PdfCanvas
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.font.PDType1Font
import org.apache.pdfbox.pdmodel.font.Standard14Fonts
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory
import java.awt.Color

class DesktopPdfCanvas(
    private val doc: PDDocument,
    private val contentStream: PDPageContentStream
) :
    PdfCanvas {
    override fun drawRect(x: Float, y: Float, width: Float, height: Float, color: String) {
        val c = Color.RED
        contentStream.setNonStrokingColor(c.red.toFloat(), c.green.toFloat(), c.blue.toFloat())
        contentStream.addRect(x, y, width, height)
        contentStream.fill()
    }

    override fun drawText(text: String, x: Float, y: Float, fontSize: Float) {
        val c = Color.BLACK
        contentStream.beginText()
        // contentStream.setTextMatrix(Matrix(1f, 0f, 0f, -1f, 0f, 0f))
        contentStream.setNonStrokingColor(c.red.toFloat(), c.green.toFloat(), c.blue.toFloat())
        contentStream.setFont(PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN), fontSize)
        contentStream.newLineAtOffset(x, y)
        contentStream.showText(text)
        contentStream.endText()
    }

    override fun drawImage(x: Float, y: Float, image: ImageBitmap) {
        TODO("Not yet implemented")
    }

    override fun renderPage(bitmap: ImageBitmap) {
        val pdImage = LosslessFactory.createFromImage(doc, bitmap.toAwtImage())
        val a4Width = 595f
        val a4Height = 842f
        contentStream.drawImage(pdImage, 0f, 0f, a4Width, a4Height)
    }
}
