package com.coderbdk.cvbuilder.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
@SerialName("CVDivider")
data class CVDivider(
    override val name: String = "Divider",
    override val layoutProperties: LayoutProperties = LayoutProperties(),
    val orientation: Orientation = Orientation.Horizontal,
    val thickness: Float = 1.0f,
    val color: String = "#FF000000",
    @Transient
    override val properties: Map<String, ComponentProperty> = mapOf(
        PropertyKeys.CVDividerKeys.ORIENTATION to EnumProperty(
            name = PropertyKeys.CVDividerKeys.ORIENTATION,
            value = orientation,
            options = Orientation.entries
        ),
        PropertyKeys.CVDividerKeys.THICKNESS to FloatProperty(
            name = PropertyKeys.CVDividerKeys.THICKNESS,
            value = thickness,
        ),
        PropertyKeys.CVDividerKeys.COLOR to StringProperty(
            name = PropertyKeys.CVDividerKeys.COLOR,
            value = color,
        )

    )
) : Component() {
    @Serializable
    enum class Orientation { Vertical, Horizontal }
}