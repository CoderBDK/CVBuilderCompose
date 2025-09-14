package com.coderbdk.cvbuilder

import com.coderbdk.cvbuilder.data.model.CVTemplate
import com.coderbdk.cvbuilder.util.toCVTemplate
import com.coderbdk.cvbuilder.util.toJsonString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.swing.JFileChooser

class DesktopCVFileHandler : CVFileHandler {
    override suspend fun export(template: CVTemplate) {
        val fileChooser = JFileChooser().apply {
            dialogTitle = "Save CV as JSON"
            fileSelectionMode = JFileChooser.FILES_ONLY
        }

        val result = withContext(Dispatchers.Main) {
            fileChooser.showSaveDialog(null)
        }

        if (result == JFileChooser.APPROVE_OPTION) {
            var file = fileChooser.selectedFile

            if (!file.name.endsWith(".json")) {
                file = File(file.parentFile, file.name + ".json")
            }

            val jsonString = template.toJsonString()
            file.writeText(jsonString)
        }
    }

    override suspend fun import(): CVTemplate? {
        val fileChooser = JFileChooser()
        fileChooser.dialogTitle = "Select JSON file"
        fileChooser.fileSelectionMode = JFileChooser.FILES_ONLY

        val result = withContext(Dispatchers.Main) {
            fileChooser.showOpenDialog(null)
        }

        if (result == JFileChooser.APPROVE_OPTION) {
            val selectedFile = fileChooser.selectedFile
            if(selectedFile.extension == "json"){
                val jsonString = selectedFile.readText()
                return jsonString.toCVTemplate()
            }
        }
        return null
    }
}