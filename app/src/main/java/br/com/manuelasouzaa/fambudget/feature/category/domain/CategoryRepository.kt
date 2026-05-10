package br.com.manuelasouzaa.fambudget.feature.category.domain

import androidx.compose.ui.graphics.Color
import br.com.manuelasouzaa.fambudget.core.network.resource.Resource
import br.com.manuelasouzaa.fambudget.feature.category.ui.model.CategorySections

interface CategoryRepository {

    suspend fun getUserCategories(): Resource<CategorySections>

    suspend fun createCategory(name: String, color: Color): Resource<Unit>

    suspend fun updateCategory(id: Int, name: String, color: Color): Resource<Unit>

    suspend fun deleteCategory(id: Int): Resource<Unit>

}
