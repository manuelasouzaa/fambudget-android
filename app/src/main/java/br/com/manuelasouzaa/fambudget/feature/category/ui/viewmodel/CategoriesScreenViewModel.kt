package br.com.manuelasouzaa.fambudget.feature.category.ui.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.manuelasouzaa.fambudget.core.network.resource.Resource
import br.com.manuelasouzaa.fambudget.feature.category.domain.CategoryRepository
import br.com.manuelasouzaa.fambudget.feature.category.ui.model.CategoryUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CategoriesScreenViewModel(
    private val repository: CategoryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<CategoriesUiState>(CategoriesUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init { load() }

    fun load() {
        viewModelScope.launch {
            _uiState.value = CategoriesUiState.Loading
            when (val result = repository.getUserCategories()) {
                is Resource.Success -> {
                    _uiState.value = CategoriesUiState.Success(
                        defaultCategories = result.data.default,
                        userCategories = result.data.user
                    )
                }
                is Resource.Error -> _uiState.value = CategoriesUiState.Error
            }
        }
    }

    fun addCategory(name: String, color: Color) {
        viewModelScope.launch {
            when (repository.createCategory(name, color)) {
                is Resource.Success -> load()
                is Resource.Error -> {}
            }
        }
    }

    fun editCategory(id: Int, name: String, color: Color) {
        viewModelScope.launch {
            when (repository.updateCategory(id, name, color)) {
                is Resource.Success -> load()
                is Resource.Error -> {}
            }
        }
    }

    fun deleteCategory(id: Int) {
        val current = _uiState.value
        if (current is CategoriesUiState.Success) {
            _uiState.value = current.copy(
                userCategories = current.userCategories.filter { it.id != id }
            )
        }
        viewModelScope.launch {
            repository.deleteCategory(id)
        }
    }
}

sealed class CategoriesUiState {
    data object Loading : CategoriesUiState()
    data object Error : CategoriesUiState()
    data class Success(
        val defaultCategories: List<CategoryUiModel>,
        val userCategories: List<CategoryUiModel>
    ) : CategoriesUiState()
}
