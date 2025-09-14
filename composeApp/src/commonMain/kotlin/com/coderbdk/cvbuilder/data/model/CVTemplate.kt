package com.coderbdk.cvbuilder.data.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.Resource

@Serializable
data class CVTemplate(
    val name: String = "CV Template",
    val pageWidth: Int = 1323,
    val pageHeight: Int = 1870,
    val pages: List<CVPage> = emptyList(),
    @Transient
    val imageResThumb: Resource? = null
)
