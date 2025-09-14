package com.coderbdk.cvbuilder.data.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.json.JsonClassDiscriminator



@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonClassDiscriminator("type")
sealed class Component {
    abstract val name: String
    abstract val layoutProperties: LayoutProperties
    abstract val properties: Map<String,ComponentProperty>
}

@Serializable
@SerialName("ComponentProperty")
sealed class ComponentProperty {
    abstract val name: String
}

@Serializable
@SerialName("StringProperty")
data class StringProperty(
    override val name: String,
    val value: String
) : ComponentProperty()

@Serializable
@SerialName("IntProperty")
data class IntProperty(
    override val name: String,
    val value: Int
) : ComponentProperty()

@Serializable
@SerialName("FloatProperty")
data class FloatProperty(
    override val name: String,
    val value: Float
) : ComponentProperty()

@Serializable
@SerialName("EnumProperty")
data class EnumProperty<T: Enum<T>>(
    override val name: String,
    val value: T,
    @Transient
    val options: List<T> = emptyList()
) : ComponentProperty()

@Serializable
@SerialName("BoolProperty")
data class BoolProperty(
    override val name: String,
    val value: Boolean
) : ComponentProperty()

interface Hintable {
    val hint: String
}

@Serializable
@SerialName("LayoutProperties")
data class LayoutProperties(
    val width: Int = 0,
    val height: Int = 0,
    val weight: Float = -1f,
    val background: String = "#00000000",
    val paddingStart: Int = 0,
    val paddingTop: Int = 0,
    val paddingEnd: Int = 0,
    val paddingBottom: Int = 0,
) {
    @Transient
    val properties = mapOf(
        PropertyKeys.WIDTH to IntProperty(PropertyKeys.WIDTH, width),
        PropertyKeys.HEIGHT to IntProperty(PropertyKeys.HEIGHT, height),
        PropertyKeys.WEIGHT to FloatProperty( PropertyKeys.WEIGHT, weight),
        PropertyKeys.BACKGROUND to StringProperty( PropertyKeys.BACKGROUND, background),
        PropertyKeys.PADDING_START to IntProperty(PropertyKeys.PADDING_START, paddingStart),
        PropertyKeys.PADDING_TOP to IntProperty(PropertyKeys.PADDING_TOP, paddingTop),
        PropertyKeys.PADDING_END to IntProperty(PropertyKeys.PADDING_END, paddingEnd),
        PropertyKeys.PADDING_BOTTOM to IntProperty(PropertyKeys.PADDING_BOTTOM, paddingBottom),
    )
}

object PropertyKeys {
    const val WIDTH = "width"
    const val HEIGHT = "height"
    const val WEIGHT = "weight"
    const val BACKGROUND = "background"
    const val PADDING_START = "paddingStart"
    const val PADDING_TOP = "paddingTop"
    const val PADDING_END = "paddingEnd"
    const val PADDING_BOTTOM = "paddingBottom"

    object CVLayoutKeys {
        const val ORIENTATION = "orientation"
        const val ARRANGEMENT = "arrangement"
        const val ALIGNMENT = "alignment"
    }

    object CVTextKeys {
        const val TEXT = "text"
        const val FONT_SIZE = "fontSize"
        const val BOLD = "bold"
        const val TEXT_COLOR = "textColor"
        const val TEXT_ALIGN = "textAlign"
        const val FONT_FAMILY = "fontFamily"
    }
    object CVDividerKeys {
        const val ORIENTATION = "orientation"
        const val COLOR = "color"
        const val THICKNESS = "thickness"
    }
    object CVListKeys {
        const val FONT_SIZE = "fontSize"
        const val BOLD = "bold"
        const val TEXT_COLOR = "textColor"
        const val TEXT_ALIGN = "textAlign"
        const val FONT_FAMILY = "fontFamily"
        const val STYLE = "style"
    }
    object CVImageKeys {
        const val CONTENT_SCALE = "contentScale"
    }
}
