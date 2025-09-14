package com.coderbdk.cvbuilder.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CVPage(
    val components: List<Component> = emptyList()
)