package com.coderbdk.cvbuilder.data.repository

import com.coderbdk.cvbuilder.data.model.CVLayout
import com.coderbdk.cvbuilder.data.model.Component

class EditorRepositoryImpl : EditorRepository {
    override fun updateDeepComponent(
        components: List<Component>,
        indices: List<Int>,
        update: (Component) -> Component
    ): List<Component> {
        if (indices.isEmpty()) return components

        val index = indices.first()
        val rest = indices.drop(1)

        return components.mapIndexed { i, comp ->
            if (i != index) comp
            else {
                if (comp is CVLayout && rest.isNotEmpty()) {
                    comp.copy(
                        children = updateDeepComponent(comp.children, rest, update)
                    )
                } else {
                    update(comp)
                }
            }
        }
    }

    override fun insertComponent(
        components: List<Component>,
        indices: List<Int>,
        newComponent: Component
    ): List<Component> {
        if (indices.isEmpty()) {
            // insert at root level
            return components + newComponent
        }

        val index = indices.first()
        val rest = indices.drop(1)

        return components.mapIndexed { i, comp ->
            if (i != index) comp
            else {
                if (comp is CVLayout && rest.isNotEmpty()) {
                    // go deeper
                    comp.copy(
                        children = insertComponent(comp.children, rest, newComponent)
                    )
                } else if (comp is CVLayout && rest.isEmpty()) {
                    // insert inside this Layout at the end
                    comp.copy(
                        children = comp.children + newComponent
                    )
                } else {
                    // leaf node cannot have children, just return itself
                    comp
                }
            }
        }
    }

    override fun insertDeepComponent(
        components: List<Component>,
        indices: List<Int>,
        newComponent: Component,
        before: Boolean
    ): List<Component> {
        if (indices.isEmpty()) {
            val list = components.toMutableList()
            if (before) list.add(0, newComponent) else list.add(newComponent)
            return list.toList()
        }

        val index = indices.first()
        val rest = indices.drop(1)

        return components.flatMapIndexed { i, comp ->
            if (i != index) listOf(comp)
            else {
                if (comp is CVLayout && rest.isNotEmpty()) {
                    listOf(
                        comp.copy(
                            children = insertDeepComponent(
                                comp.children,
                                rest,
                                newComponent,
                                before
                            )
                        )
                    )
                } else {
                    if (before) listOf(newComponent, comp) else listOf(comp, newComponent)
                }
            }
        }
    }


    override fun insertSurroundComponent(
        components: List<Component>,
        indices: List<Int>,
        newComponent: Component
    ): List<Component> {
        require(newComponent is CVLayout) { "newComponent must be a CVLayout" }

        if (indices.isEmpty()) {
            // wrap ALL root components inside newComponent
            return listOf(newComponent.copy(children = components))
        }

        val index = indices.first()
        val rest = indices.drop(1)

        return components.mapIndexed { i, comp ->
            if (i != index) {
                comp
            } else {
                if (comp is CVLayout && rest.isNotEmpty()) {
                    // go deeper inside layout
                    comp.copy(
                        children = insertSurroundComponent(comp.children, rest, newComponent)
                    )
                } else {
                    newComponent.copy(children = listOf(comp))
                }
            }
        }
    }

    override fun deleteDeepComponent(
        components: List<Component>,
        indices: List<Int>
    ): List<Component> {
        if (indices.isEmpty()) return components

        val index = indices.first()
        val rest = indices.drop(1)

        return components.mapIndexedNotNull { i, comp ->
            if (i != index) comp
            else {
                if (rest.isEmpty()) {
                    null
                } else if (comp is CVLayout) {
                    // recursively delete in children
                    comp.copy(children = deleteDeepComponent(comp.children, rest))
                } else {
                    comp
                }
            }
        }
    }
}