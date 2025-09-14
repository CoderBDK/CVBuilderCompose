package com.coderbdk.cvbuilder.data.model

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
@SerialName("CVList")
data class CVList(
    override val name: String = "List",
    override val layoutProperties: LayoutProperties = LayoutProperties(),
    val items: List<String> = listOf("Item 1", "Item 2", "Item3"),
    val fontSize: Int = 24,
    val textColor: String = "#FF000000",
    val textAlign: Align = Align.Unspecified,
    val fontFamily: Font = Font.None,
    val style: Style = Style.Numbered,
    val bold: Boolean = false,
    @Transient
    override val properties: Map<String, ComponentProperty> = mapOf(
        PropertyKeys.CVListKeys.FONT_SIZE to IntProperty(
            PropertyKeys.CVListKeys.FONT_SIZE,
            fontSize
        ),
        PropertyKeys.CVListKeys.TEXT_COLOR to StringProperty(
            PropertyKeys.CVListKeys.TEXT_COLOR,
            textColor
        ),
        PropertyKeys.CVListKeys.TEXT_ALIGN to EnumProperty(
            name = PropertyKeys.CVListKeys.TEXT_ALIGN,
            value = textAlign,
            options = Align.entries
        ),
        PropertyKeys.CVListKeys.STYLE to EnumProperty(
            name = PropertyKeys.CVListKeys.STYLE,
            value = style,
            options = Style.entries
        ),
        PropertyKeys.CVListKeys.FONT_FAMILY to EnumProperty(
            name = PropertyKeys.CVListKeys.FONT_FAMILY,
            value = fontFamily,
            options = Font.entries
        ),
        PropertyKeys.CVListKeys.BOLD to BoolProperty(
            name = PropertyKeys.CVListKeys.BOLD,
            value = bold,
        ),
    )

) : Component() {
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
    enum class Style {
        Bullet,
        Dashed,
        Numbered,
    }
}
