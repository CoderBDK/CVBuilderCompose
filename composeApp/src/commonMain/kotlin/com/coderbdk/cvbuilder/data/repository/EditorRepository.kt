package com.coderbdk.cvbuilder.data.repository

import com.coderbdk.cvbuilder.data.model.Component

interface EditorRepository {
    fun updateDeepComponent(
        components: List<Component>,
        indices: List<Int>,
        update: (Component) -> Component
    ): List<Component>

    fun insertComponent(
        components: List<Component>,
        indices: List<Int>,
        newComponent: Component
    ): List<Component>

    fun insertDeepComponent(
        components: List<Component>,
        indices: List<Int>,
        newComponent: Component,
        before: Boolean
    ): List<Component>

    fun insertSurroundComponent(
        components: List<Component>,
        indices: List<Int>,
        newComponent: Component
    ): List<Component>

    fun deleteDeepComponent(
        components: List<Component>,
        indices: List<Int>
    ): List<Component>
}