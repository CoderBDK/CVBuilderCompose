package com.coderbdk.cvbuilder.pdf

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameMillis
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.unit.dp
import com.coderbdk.cvbuilder.data.model.CVTemplate
import com.coderbdk.cvbuilder.getPlatform
import com.coderbdk.cvbuilder.ui.editor.RenderPreviewPage

@Composable
fun ExportComposePdf(
    template: CVTemplate,
    onCompleted: () -> Unit,
    modifier: Modifier = Modifier
) {
    var currentRenderPage by remember { mutableStateOf(0) }
    val graphicsLayer = rememberGraphicsLayer()

    LaunchedEffect(Unit) {
        getPlatform().pdfExporter.export(
            template = template,
            onPageRequest = { pageIndex ->
                currentRenderPage = pageIndex
            },
            onPageRender = { pageIndex, pdfCanvas ->
                withFrameNanos {}
                val bitmap = graphicsLayer.toImageBitmap()
                pdfCanvas.renderPage(bitmap)
            },
        )
        // delay(3000)
        onCompleted()
    }

    RenderingDialog(
        currentRenderPage = currentRenderPage,
        totalPages = template.pages.size,
    )

    RenderPreviewPage(
        page = template.pages[currentRenderPage],
        modifier = modifier
            .drawWithContent {
                // call record to capture the content in the graphics layer
                graphicsLayer.record {
                    // draw the contents of the composable into the graphics layer
                    this@drawWithContent.drawContent()
                }
                // draw the graphics layer on the visible canvas
                drawLayer(graphicsLayer)
            }
    )
}

@Composable
fun RenderingDialog(
    currentRenderPage: Int,
    totalPages: Int,
) {
    AlertDialog(
        onDismissRequest = { },
        confirmButton = {},
        dismissButton = {},
        title = {
            Text(
                text = "Exporting PDF",
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LinearProgressIndicator(
                    progress = { currentRenderPage / totalPages.toFloat() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                )

                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Rendering page $currentRenderPage of $totalPages",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    )
}
