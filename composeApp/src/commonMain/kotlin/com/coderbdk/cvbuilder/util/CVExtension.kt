package com.coderbdk.cvbuilder.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import com.coderbdk.cvbuilder.data.model.BoolProperty
import com.coderbdk.cvbuilder.data.model.CVDivider
import com.coderbdk.cvbuilder.data.model.CVImage
import com.coderbdk.cvbuilder.data.model.CVLayout
import com.coderbdk.cvbuilder.data.model.CVList
import com.coderbdk.cvbuilder.data.model.CVTemplate
import com.coderbdk.cvbuilder.data.model.CVText
import com.coderbdk.cvbuilder.data.model.Component
import com.coderbdk.cvbuilder.data.model.ComponentProperty
import com.coderbdk.cvbuilder.data.model.EnumProperty
import com.coderbdk.cvbuilder.data.model.FloatProperty
import com.coderbdk.cvbuilder.data.model.IntProperty
import com.coderbdk.cvbuilder.data.model.PropertyKeys
import com.coderbdk.cvbuilder.data.model.StringProperty
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic


fun CVTemplate.toJsonString(): String {
    val module = SerializersModule {
        polymorphic(Component::class) {
            subclass(CVText::class, CVText.serializer())
            subclass(CVLayout::class, CVLayout.serializer())
            subclass(CVDivider::class, CVDivider.serializer())
        }

    }
    val json = Json {
        ignoreUnknownKeys = true
        serializersModule = module
        classDiscriminator = "type"
        prettyPrint = true
        encodeDefaults = true

    }

    return json.encodeToString(this)
}

fun String.toCVTemplate(): CVTemplate {
    val module = SerializersModule {
        polymorphic(Component::class) {
            subclass(CVText::class, CVText.serializer())
            subclass(CVLayout::class, CVLayout.serializer())
            subclass(CVDivider::class, CVDivider.serializer())
        }

    }
    val json = Json {
        ignoreUnknownKeys = true
        serializersModule = module
        classDiscriminator = "type"
        prettyPrint = true
        encodeDefaults = true

    }

    return json.decodeFromString(this)
}

fun Component.updateLayoutProperty(name: String, newValue: Any): Component {
    val updatedProps = layoutProperties.properties.toMutableMap()
    val prop = updatedProps[name]

    if (prop != null) {
        updatedProps[name] = when (prop) {
            is StringProperty -> prop.copy(value = newValue as String)
            is IntProperty -> prop.copy(value = newValue as Int)
            is FloatProperty -> prop.copy(value = newValue as Float)
            is EnumProperty<*> -> {
                @Suppress("UNCHECKED_CAST")
                (prop as EnumProperty<Enum<*>>).copy(value = newValue as Enum<*>)
            }

            is BoolProperty -> prop.copy(value = newValue as Boolean)
        }
    }
    val uProps = updatedProps.toMap()

    val uLayoutProperties = layoutProperties.copy(
        width = uProps.getPropertyValue(PropertyKeys.WIDTH, 0),
        height = uProps.getPropertyValue(PropertyKeys.HEIGHT, 0),
        weight = uProps.getPropertyValue(PropertyKeys.WEIGHT, -1f),
        background = uProps.getPropertyValue(PropertyKeys.BACKGROUND, "#00000000"),
        paddingStart = uProps.getPropertyValue(PropertyKeys.PADDING_START, 0),
        paddingTop = uProps.getPropertyValue(PropertyKeys.PADDING_TOP, 0),
        paddingEnd = uProps.getPropertyValue(PropertyKeys.PADDING_END, 0),
        paddingBottom = uProps.getPropertyValue(PropertyKeys.PADDING_BOTTOM, 0),
    )

    return when (this) {
        is CVText -> copy(
            layoutProperties = uLayoutProperties,
        )

        is CVLayout -> copy(
            layoutProperties = uLayoutProperties,
        )

        is CVDivider -> copy(
            layoutProperties = uLayoutProperties,
        )

        is CVList -> {
            copy(
                layoutProperties = uLayoutProperties,
            )
        }

        is CVImage -> {
            copy(
                layoutProperties = uLayoutProperties,
            )
        }
    }
}

fun Component.updateProperty(name: String, newValue: Any): Component {
    val updatedProps = properties.toMutableMap()
    val prop = updatedProps[name]

    if (prop != null) {
        updatedProps[name] = when (prop) {
            is StringProperty -> prop.copy(value = newValue as String)
            is IntProperty -> prop.copy(value = newValue as Int)
            is FloatProperty -> prop.copy(value = newValue as Float)
            is EnumProperty<*> -> {
                @Suppress("UNCHECKED_CAST")
                (prop as EnumProperty<Enum<*>>).copy(value = newValue as Enum<*>)
            }

            is BoolProperty -> prop.copy(value = newValue as Boolean)
        }
    }
    val uProps = updatedProps.toMap()

    return when (this) {
        is CVText -> copy(
            text = uProps.getPropertyValue(PropertyKeys.CVTextKeys.TEXT, "Text"),
            fontSize = uProps.getPropertyValue(PropertyKeys.CVTextKeys.FONT_SIZE, 24),
            textColor = uProps.getPropertyValue(PropertyKeys.CVTextKeys.TEXT_COLOR, "#FF000000"),
            textAlign = uProps.getPropertyValue(
                PropertyKeys.CVTextKeys.TEXT_ALIGN,
                CVText.Align.Start
            ),
            fontFamily = uProps.getPropertyValue(
                PropertyKeys.CVTextKeys.FONT_FAMILY,
                CVText.Font.None
            ),
            bold = uProps.getPropertyValue(PropertyKeys.CVTextKeys.BOLD, false),
            properties = uProps
        )

        is CVLayout -> copy(
            orientation = uProps.getPropertyValue(
                PropertyKeys.CVLayoutKeys.ORIENTATION,
                CVLayout.Orientation.Horizontal
            ),
            arrangement = uProps.getPropertyValue(
                PropertyKeys.CVLayoutKeys.ARRANGEMENT,
                CVLayout.Arrangement.Start
            ),
            alignment = uProps.getPropertyValue(
                PropertyKeys.CVLayoutKeys.ALIGNMENT,
                CVLayout.Alignment.Top
            ),
            properties = uProps
        )

        is CVDivider -> copy(
            orientation = uProps.getPropertyValue(
                PropertyKeys.CVDividerKeys.ORIENTATION,
                CVDivider.Orientation.Horizontal
            ),
            thickness = uProps.getPropertyValue(PropertyKeys.CVDividerKeys.THICKNESS, 1f),
            color = uProps.getPropertyValue(PropertyKeys.CVDividerKeys.COLOR, "#FF000000"),
            properties = uProps
        )

        is CVList -> copy(
            fontSize = uProps.getPropertyValue(PropertyKeys.CVListKeys.FONT_SIZE, 24),
            textColor = uProps.getPropertyValue(PropertyKeys.CVListKeys.TEXT_COLOR, "#FF000000"),
            textAlign = uProps.getPropertyValue(
                PropertyKeys.CVListKeys.TEXT_ALIGN,
                CVList.Align.Start
            ),
            fontFamily = uProps.getPropertyValue(
                PropertyKeys.CVListKeys.FONT_FAMILY,
                CVList.Font.None
            ),
            style = uProps.getPropertyValue(PropertyKeys.CVListKeys.STYLE, CVList.Style.Numbered),
            bold = uProps.getPropertyValue(PropertyKeys.CVListKeys.BOLD, false),
            properties = uProps
        )

        is CVImage -> copy(
            scale = uProps.getPropertyValue(
                PropertyKeys.CVImageKeys.CONTENT_SCALE,
                CVImage.Scale.Fit
            ),
            properties = uProps
        )
    }
}

@Suppress("UNCHECKED_CAST")
fun <T> Component.getPropertyValue(key: String, defaultValue: T): T =
    this.properties[key]?.let {
        when (it) {
            is StringProperty -> it.value as? T
            is IntProperty -> it.value as? T
            is FloatProperty -> it.value as? T
            is BoolProperty -> it.value as? T
            is EnumProperty<*> -> it.value as? T
        }
    } ?: defaultValue

@Suppress("UNCHECKED_CAST")
private fun <T> Map<String, ComponentProperty>.getPropertyValue(key: String, defaultValue: T): T =
    this[key]?.let {
        when (it) {
            is StringProperty -> it.value as? T
            is IntProperty -> it.value as? T
            is FloatProperty -> it.value as? T
            is BoolProperty -> it.value as? T
            is EnumProperty<*> -> it.value as? T
        }
    } ?: defaultValue

data class CVColor(val value: UInt) {
    companion object {
        fun parse(hex: String): CVColor {
            val cleaned = hex.removePrefix("#")
            val parsed = when (cleaned.length) {
                6 -> ("FF$cleaned").toUIntOrNull(16)
                8 -> cleaned.toUIntOrNull(16)
                else -> 0xFF000000u
            }
            return CVColor(parsed ?: 0xFF000000u)
        }

        fun fromArgb(a: Int, r: Int, g: Int, b: Int): CVColor {
            val value = ((a and 0xFF) shl 24) or
                    ((r and 0xFF) shl 16) or
                    ((g and 0xFF) shl 8) or
                    (b and 0xFF)
            return CVColor(value.toUInt())
        }
    }

    val alpha: Int get() = ((value shr 24) and 0xFFu).toInt()
    val red: Int get() = ((value shr 16) and 0xFFu).toInt()
    val green: Int get() = ((value shr 8) and 0xFFu).toInt()
    val blue: Int get() = (value and 0xFFu).toInt()
}

fun CVColor.toColor(): Color {

    return Color(
        alpha = this.alpha / 255f,
        red = this.red / 255f,
        green = this.green / 255f,
        blue = this.blue / 255f
    )
}