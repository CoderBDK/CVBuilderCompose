package com.coderbdk.cvbuilder.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
@SerialName("CVLayout")
data class CVLayout(
    override val name: String = "Layout",
    override val layoutProperties: LayoutProperties = LayoutProperties(),
    val children: List<Component>,
    val orientation: Orientation = Orientation.Vertical,
    val arrangement: Arrangement = if (orientation == Orientation.Vertical) Arrangement.Top else Arrangement.Start,
    val alignment: Alignment = if (orientation == Orientation.Vertical) Alignment.Start else Alignment.Top,
    @Transient
    override val properties: Map<String, ComponentProperty> = mapOf(
        PropertyKeys.CVLayoutKeys.ORIENTATION to EnumProperty(
            name = "orientation",
            value = orientation,
            options = Orientation.entries
        ),
        PropertyKeys.CVLayoutKeys.ARRANGEMENT to EnumProperty(
            name = "arrangement",
            value = arrangement,
            options = Arrangement.entries
        ),
        PropertyKeys.CVLayoutKeys.ALIGNMENT to EnumProperty(
            name = "alignment",
            value = alignment,
            options = Alignment.entries
        ),
    )
) : Component() {

    @Serializable
    enum class Orientation { Vertical, Horizontal }

    @Serializable
    enum class Arrangement(override val hint: String = "Both") : Hintable {
        Top("Vertical"),
        Bottom("Vertical"),
        Start("Horizontal"),
        End("Horizontal"),
        Center, SpaceEvenly, SpaceBetween, SpaceAround
    }

    @Serializable
    enum class Alignment(override val hint: String) : Hintable {
        Top("Vertical"),
        Bottom("Vertical"),
        CenterVertically("Vertical"),
        Start("Horizontal"),
        End("Horizontal"),
        CenterHorizontally("Horizontal")
    }
}