package com.coderbdk.cvbuilder.ui.editor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.coderbdk.cvbuilder.data.model.CVLayout
import com.coderbdk.cvbuilder.data.model.CVPage
import com.coderbdk.cvbuilder.data.model.CVTemplate
import com.coderbdk.cvbuilder.data.model.CVText
import com.coderbdk.cvbuilder.data.model.Component
import com.coderbdk.cvbuilder.data.repository.EditorRepository
import com.coderbdk.cvbuilder.ui.navigation.Screen
import com.coderbdk.cvbuilder.util.toCVTemplate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class EditorUiState(
    val template: CVTemplate,
    val currentPage: CVPage,
    val currentPageIndex: Int = 0,
    val selectedComponentDepthIndex: String? = null,
    val selectedComponent: Component? = null,
    val selectedInsertComponent: Component? = null,
    val copyComponent: Component? = null,
    val isPdfExporting: Boolean = false,
)

sealed class EditorUiEvent {
    data class SelectComponent(val depthIndex: String, val component: Component) : EditorUiEvent()
    data class SelectParentComponent(val depthIndex: String) : EditorUiEvent()
    data object CancelSelectComponent : EditorUiEvent()
    data class Update(val depthIndex: String, val newComponent: Component) : EditorUiEvent()

    data class Insert(
        val depthIndex: String,
        val newComponent: Component
    ) : EditorUiEvent()

    data class InsertBefore(
        val depthIndex: String,
        val newComponent: Component
    ) : EditorUiEvent()

    data class InsertAfter(
        val depthIndex: String,
        val newComponent: Component
    ) : EditorUiEvent()

    data class InsertSurround(
        val depthIndex: String,
        val newComponent: Component
    ) : EditorUiEvent()

    data class Delete(val depthIndex: String) : EditorUiEvent()

    data class ShowInsertDialog(val component: Component?) : EditorUiEvent()
    data class LoadCVTemplate(val template: CVTemplate) : EditorUiEvent()
    data class PdfExporting(val mode: Boolean) : EditorUiEvent()
    data object PrevPage : EditorUiEvent()
    data object NextPage : EditorUiEvent()
    data object CreateNewPage : EditorUiEvent()
    data object DeleteCurrentPage : EditorUiEvent()
    data object CopySelectedComponent : EditorUiEvent()
    data object DeleteCopiedComponent : EditorUiEvent()
}

class EditorViewModel(
    savedStateHandle: SavedStateHandle,
    private val editorRepository: EditorRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        EditorUiState(
            template = CVTemplate(),
            currentPage = CVPage()
        )
    )
    val uiState = _uiState.asStateFlow()

    init {

        val template = savedStateHandle.toRoute<Screen.Editor>().templateData.toCVTemplate()

        _uiState.update {
            it.copy(
                template = template,
                currentPage = template.pages[0]
            )
        }
    }

    fun onEvent(event: EditorUiEvent) {
        when (event) {
            is EditorUiEvent.Delete -> {
                deleteComponent(event)
            }

            is EditorUiEvent.Insert -> {
                insertComponent(event)
            }

            is EditorUiEvent.InsertBefore -> {
                insertComponentBefore(event)
            }

            is EditorUiEvent.InsertAfter -> {
                insertComponentAfter(event)
            }

            is EditorUiEvent.InsertSurround -> {
                insertComponentSurround(event)
            }

            is EditorUiEvent.SelectComponent -> {
                selectComponent(event)
            }

            is EditorUiEvent.SelectParentComponent -> {
                selectParentComponent(event)
            }

            is EditorUiEvent.CancelSelectComponent -> {
                cancelSelectComponent()
            }

            is EditorUiEvent.Update -> {
                updateComponent(event)
            }

            is EditorUiEvent.ShowInsertDialog -> {
                showInsertDialog(event)
            }

            is EditorUiEvent.LoadCVTemplate -> {
                loadCVTemplate(event)
            }

            is EditorUiEvent.PdfExporting -> {
                pdfExporting(event)
            }

            is EditorUiEvent.PrevPage -> prevPage()
            is EditorUiEvent.NextPage -> nextPage()
            is EditorUiEvent.CreateNewPage -> {
                createNewPage()
            }

            is EditorUiEvent.DeleteCurrentPage -> {
                deleteCurrentPage()
            }

            is EditorUiEvent.CopySelectedComponent -> copySelectedComponent()
            is EditorUiEvent.DeleteCopiedComponent -> deleteCopiedComponent()
        }
    }


    private fun findComponent(depthIndex: String): Component? {
        val indexArray = depthIndex.split(" ").map { it.toInt() }
        var components = uiState.value.currentPage.components
        var rComponent = components[indexArray[0]]
        for (i in 0 until indexArray.size) {
            val component = components[indexArray[i]]
            when (component) {
                is CVLayout -> {
                    components = component.children
                    rComponent = component
                }

                else -> {
                    rComponent = component
                }
            }
        }
        return rComponent
    }

    private fun deleteComponent(event: EditorUiEvent.Delete) {
        val indexArray = event.depthIndex.split(" ").map { it.toInt() }
        _uiState.update {
            val components = editorRepository.deleteDeepComponent(
                it.currentPage.components,
                indexArray
            )
            val updatedPage = it.currentPage.copy(
                components = components.ifEmpty {
                    listOf(
                        CVLayout(
                            orientation = CVLayout.Orientation.Vertical,
                            children = emptyList()
                        )
                    )
                }
            )
            it.copy(
                currentPage = updatedPage,
                template = it.template.copy(
                    pages = it.template.pages.replaceAt(it.currentPageIndex, updatedPage)
                ),
                selectedComponentDepthIndex = null,
                selectedComponent = null,
            )
        }
    }

    private fun insertComponent(event: EditorUiEvent.Insert) {
        val indexArray = event.depthIndex.split(" ").map { it.toInt() }
        _uiState.update {
            val updatedPage = it.currentPage.copy(
                components = editorRepository.insertComponent(
                    it.currentPage.components,
                    indexArray,
                    event.newComponent
                )
            )
            it.copy(
                currentPage = updatedPage,
                template = it.template.copy(
                    pages = it.template.pages.replaceAt(it.currentPageIndex, updatedPage)
                ),
                selectedInsertComponent = null
            )
        }
    }

    private fun insertComponentBefore(event: EditorUiEvent.InsertBefore) {
        val indexArray = event.depthIndex.split(" ").map { it.toInt() }
        val updatedPage = uiState.value.currentPage.copy(
            components = editorRepository.insertDeepComponent(
                uiState.value.currentPage.components,
                indexArray,
                event.newComponent,
                before = true
            )
        )
        val newDepthIndex = indexArray.toMutableList().apply {
            set(lastIndex, get(lastIndex) + 1)
        }.joinToString(" ")

        _uiState.update {
            it.copy(
                currentPage = updatedPage,
                template = it.template.copy(
                    pages = it.template.pages.replaceAt(it.currentPageIndex, updatedPage)
                ),
                selectedInsertComponent = null,
                selectedComponentDepthIndex = newDepthIndex
            )
        }
    }

    private fun insertComponentAfter(event: EditorUiEvent.InsertAfter) {
        val indexArray = event.depthIndex.split(" ").map { it.toInt() }
        _uiState.update {
            val updatedPage = it.currentPage.copy(
                components = editorRepository.insertDeepComponent(
                    it.currentPage.components,
                    indexArray,
                    event.newComponent,
                    before = false
                )
            )

            it.copy(
                currentPage = updatedPage,
                template = it.template.copy(
                    pages = it.template.pages.replaceAt(it.currentPageIndex, updatedPage)
                ),
                selectedInsertComponent = null
            )
        }
    }


    private fun insertComponentSurround(event: EditorUiEvent.InsertSurround) {
        val indexArray = event.depthIndex.split(" ").map { it.toInt() }

        val updatedPage = uiState.value.currentPage.copy(
            components = editorRepository.insertSurroundComponent(
                uiState.value.currentPage.components,
                indexArray,
                event.newComponent,
            )
        )

        val newDepthIndex = (indexArray + 0).joinToString(" ")

        _uiState.update {
            it.copy(
                currentPage = updatedPage,
                template = it.template.copy(
                    pages = it.template.pages.replaceAt(it.currentPageIndex, updatedPage)
                ),
                selectedInsertComponent = null,
                selectedComponentDepthIndex = newDepthIndex
            )
        }
    }

    private fun selectComponent(event: EditorUiEvent.SelectComponent) {
        val alreadySelected = uiState.value.selectedComponentDepthIndex == event.depthIndex
        _uiState.update {
            if (alreadySelected) {
                it.copy(
                    selectedComponentDepthIndex = null,
                    selectedComponent = null
                )
            } else {
                it.copy(
                    selectedComponentDepthIndex = event.depthIndex,
                    selectedComponent = event.component,//findComponent(event.depthIndex),
                )
            }
        }
    }

    private fun selectParentComponent(event: EditorUiEvent.SelectParentComponent) {
        val indexArray = event.depthIndex.split(" ").map { it.toInt() }.dropLast(1)
        val newDepthIndex = indexArray.joinToString(" ")

        _uiState.update {
            if (indexArray.isEmpty()) {
                it.copy(
                    selectedComponentDepthIndex = null,
                    selectedComponent = null
                )
            } else {
                it.copy(
                    selectedComponentDepthIndex = newDepthIndex,
                    selectedComponent = findComponent(newDepthIndex),
                )
            }
        }
    }

    private fun cancelSelectComponent() {
        _uiState.update { currentState ->
            currentState.copy(
                selectedComponentDepthIndex = null,
                selectedComponent = null
            )
        }
    }


    private fun updateComponent(event: EditorUiEvent.Update) {
        val indexArray = event.depthIndex.split(" ").map { it.toInt() }
        val newComponents = editorRepository.updateDeepComponent(
            uiState.value.currentPage.components,
            indexArray
        ) { _ -> event.newComponent }

        val updatedPage = uiState.value.currentPage.copy(
            components = newComponents,
        )
        _uiState.update { currentState ->
            currentState.copy(
                currentPage = updatedPage,
                template = currentState.template.copy(
                    pages = currentState.template.pages.replaceAt(
                        currentState.currentPageIndex,
                        updatedPage
                    )
                ),
                selectedComponent = event.newComponent
            )
        }
    }

    private fun showInsertDialog(event: EditorUiEvent.ShowInsertDialog) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedInsertComponent = event.component
            )
        }
    }

    private fun loadCVTemplate(event: EditorUiEvent.LoadCVTemplate) {
        _uiState.update {
            it.copy(
                template = event.template,
                currentPage = event.template.pages[0]
            )
        }
    }

    private fun pdfExporting(event: EditorUiEvent.PdfExporting) {
        _uiState.update {
            it.copy(
                isPdfExporting = event.mode
            )
        }
    }

    private fun prevPage() {
        _uiState.update {
            val canGo = it.currentPageIndex > 0
            val pageIndex =
                if (canGo) it.currentPageIndex - 1 else it.currentPageIndex
            it.copy(
                currentPageIndex = pageIndex,
                currentPage = it.template.pages[pageIndex],
                selectedComponentDepthIndex = if (canGo) null else it.selectedComponentDepthIndex,
                selectedComponent = if (canGo) null else it.selectedComponent,
            )
        }
    }

    private fun nextPage() {
        _uiState.update {
            val canGo = it.currentPageIndex < it.template.pages.lastIndex
            val pageIndex =
                if (canGo) it.currentPageIndex + 1 else it.currentPageIndex
            it.copy(
                currentPageIndex = pageIndex,
                currentPage = it.template.pages[pageIndex],
                selectedComponentDepthIndex = if (canGo) null else it.selectedComponentDepthIndex,
                selectedComponent = if (canGo) null else it.selectedComponent,
            )
        }
    }

    private fun createNewPage() {
        _uiState.update {
            it.copy(
                template = it.template.copy(
                    pages = it.template.pages + CVPage(
                        listOf(
                            CVLayout(
                                children = listOf(
                                    CVText(text = "New Page Created")
                                )
                            )
                        )
                    )
                )
            )
        }
    }

    private fun deleteCurrentPage() {
        _uiState.update {
            val canDelete = it.template.pages.size > 1
            val newPages =
                if (canDelete) it.template.pages - it.currentPage else it.template.pages
            it.copy(
                template = it.template.copy(
                    pages = newPages,
                ),
                currentPage = newPages.last(),
                currentPageIndex = if (newPages.size > 1) it.currentPageIndex - 1 else 0,
                selectedComponentDepthIndex = if (canDelete) null else it.selectedComponentDepthIndex,
                selectedComponent = if (canDelete) null else it.selectedComponent,
            )
        }
    }

    private fun copySelectedComponent() {
        _uiState.update {
            it.copy(
                copyComponent = it.selectedComponent
            )
        }
    }

    private fun deleteCopiedComponent() {
        _uiState.update {
            it.copy(
                copyComponent = null
            )
        }
    }

    private fun <T> List<T>.replaceAt(index: Int, newValue: T): List<T> =
        mapIndexed { i, item -> if (i == index) newValue else item }
}