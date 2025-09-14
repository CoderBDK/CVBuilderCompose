package com.coderbdk.cvbuilder.ui.home

import androidx.lifecycle.ViewModel
import com.coderbdk.cvbuilder.data.model.CVPage
import com.coderbdk.cvbuilder.data.model.CVTemplate
import com.coderbdk.cvbuilder.data.repository.TemplateRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class HomeUiState(
    val templates: List<CVTemplate> = emptyList(),
    val selectedIndex: Int? = null
)

sealed class HomeUiEvent {
    data class SelectTemplate(val index: Int) : HomeUiEvent()
}

class HomeViewModel(
    private val templateRepository: TemplateRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()


    init {
        _uiState.update {
            it.copy(
                templates = templateRepository.getCVTemplates()
            )
        }
    }

    fun onEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.SelectTemplate -> {
                _uiState.update {
                    it.copy(
                        selectedIndex = if (it.selectedIndex == event.index) null else event.index
                    )
                }
            }
        }
    }

}