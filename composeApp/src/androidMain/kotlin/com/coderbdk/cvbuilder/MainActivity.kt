package com.coderbdk.cvbuilder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.coderbdk.cvbuilder.data.model.CVLayout
import com.coderbdk.cvbuilder.data.model.CVLayout.Orientation
import com.coderbdk.cvbuilder.data.model.CVPage
import com.coderbdk.cvbuilder.data.model.CVTemplate
import com.coderbdk.cvbuilder.data.model.CVText
import com.coderbdk.cvbuilder.data.model.PropertyKeys
import com.coderbdk.cvbuilder.util.toJsonString
import com.coderbdk.cvbuilder.util.updateProperty

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        (getPlatform().imagePicker as AndroidImagePicker).register(this)

        setContent {
            App()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AppAndroidPreview() {
    val template = CVTemplate(
        pages = listOf(
            CVPage(
                components = listOf(
                    CVLayout(
                        //  name = "CVLayout",
                        children = listOf(
                            CVText(text = "Hello", fontSize = 32)
                                .updateProperty(
                                    PropertyKeys.HEIGHT, 22
                                ),
                        ),
                        orientation = Orientation.Vertical,
                    ),
                )
            )
        )
    )

    Column(
        Modifier
            .fillMaxSize()
    ) {
        Text("${template.toJsonString()}")
    }
}