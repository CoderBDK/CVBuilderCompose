package com.coderbdk.cvbuilder.data.model

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
@SerialName("CVText")
data class CVText(
    override val name: String = "Text",
    override val layoutProperties: LayoutProperties = LayoutProperties(),
    val text: String = "Text",
    val fontSize: Int = 24,
    val textColor: String = "#FF000000",
    val textAlign: Align = Align.Unspecified,
    val fontFamily: Font = Font.None,
    val bold: Boolean = false,
    @Transient
    override val properties: Map<String, ComponentProperty> = mapOf(
        PropertyKeys.CVTextKeys.TEXT to StringProperty(PropertyKeys.CVTextKeys.TEXT, text),
        PropertyKeys.CVTextKeys.FONT_SIZE to IntProperty(
            PropertyKeys.CVTextKeys.FONT_SIZE,
            fontSize
        ),
        PropertyKeys.CVTextKeys.TEXT_COLOR to StringProperty(
            PropertyKeys.CVTextKeys.TEXT_COLOR,
            textColor
        ),
        PropertyKeys.CVTextKeys.TEXT_ALIGN to EnumProperty(
            name = PropertyKeys.CVTextKeys.TEXT_ALIGN,
            value = textAlign,
            options = Align.entries
        ),
        PropertyKeys.CVTextKeys.FONT_FAMILY to EnumProperty(
            name = PropertyKeys.CVTextKeys.FONT_FAMILY,
            value = fontFamily,
            options = Font.entries
        ),
        PropertyKeys.CVTextKeys.BOLD to BoolProperty(PropertyKeys.CVTextKeys.BOLD, bold),
    )
) : Component() {
    @Serializable
    enum class Font(val fontFamily: FontFamily?) {
        None(null),
        Default(FontFamily.Default),
        Cursive(FontFamily.Cursive),
        Monospace(FontFamily.Monospace),
        SansSerif(FontFamily.SansSerif),
        Serif(FontFamily.Serif),
    }

    @Serializable
    enum class Align(val textAlign: TextAlign) {
        Left(TextAlign.Left),
        Right(TextAlign.Right),
        Center(TextAlign.Center),
        Justify(TextAlign.Justify),
        Start(TextAlign.Start),
        End(TextAlign.End),
        Unspecified(TextAlign.Unspecified)
    }
}
