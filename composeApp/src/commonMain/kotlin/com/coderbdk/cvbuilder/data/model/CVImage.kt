package com.coderbdk.cvbuilder.data.model

import androidx.compose.ui.layout.ContentScale
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
@SerialName("CVImage")
data class CVImage(
    override val name: String = "Image",
    override val layoutProperties: LayoutProperties = LayoutProperties(),
    val data: String? = null,
    val scale: Scale = Scale.Fit,
    @Transient
    override val properties: Map<String, ComponentProperty> = mapOf(
        PropertyKeys.CVImageKeys.CONTENT_SCALE to EnumProperty(
            name = PropertyKeys.CVImageKeys.CONTENT_SCALE,
            value = scale,
            options = Scale.entries
        ),
    )

) : Component() {
    @Serializable
    enum class Scale(val contentScale: ContentScale) {
        None(ContentScale.None),
        Fit(ContentScale.Fit),
        Crop(ContentScale.Crop),
        FillBounds(ContentScale.FillBounds),
        FillWidth(ContentScale.FillWidth),
        FillHeight(ContentScale.FillHeight),
        Inside(ContentScale.Inside),
    }
}
