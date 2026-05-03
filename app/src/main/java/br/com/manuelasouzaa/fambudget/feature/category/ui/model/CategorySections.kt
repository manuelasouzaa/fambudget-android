package br.com.manuelasouzaa.fambudget.feature.category.ui.model

data class CategorySections(
    val default: List<CategoryUiModel> = emptyList(),
    val user: List<CategoryUiModel> = emptyList()
)
