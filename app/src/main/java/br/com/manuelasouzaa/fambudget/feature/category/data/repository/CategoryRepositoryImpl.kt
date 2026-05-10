package br.com.manuelasouzaa.fambudget.feature.category.data.repository

import androidx.compose.ui.graphics.Color
import br.com.manuelasouzaa.fambudget.core.network.resource.Resource
import br.com.manuelasouzaa.fambudget.feature.category.data.mapper.CategoryColorMapper
import br.com.manuelasouzaa.fambudget.feature.category.data.mapper.CategoryMapper.toCategorySections
import br.com.manuelasouzaa.fambudget.feature.category.data.remote.CategoryWebClient
import br.com.manuelasouzaa.fambudget.feature.category.data.remote.model.CreateCategoryRequest
import br.com.manuelasouzaa.fambudget.feature.category.data.remote.model.UpdateCategoryRequest
import br.com.manuelasouzaa.fambudget.feature.category.domain.CategoryRepository
import br.com.manuelasouzaa.fambudget.feature.category.ui.model.CategorySections

class CategoryRepositoryImpl(private val webClient: CategoryWebClient) : CategoryRepository {

    override suspend fun getUserCategories(): Resource<CategorySections> {
        return when (val resp = webClient.getUserCategories()) {
            is Resource.Success -> Resource.Success(resp.data.toCategorySections())
            is Resource.Error -> resp
        }
    }

    override suspend fun createCategory(name: String, color: Color): Resource<Unit> {
        val request = CreateCategoryRequest(
            name = name,
            colorCategory = CategoryColorMapper.fromUiColor(color)
        )
        return when (val resp = webClient.createCategory(request)) {
            is Resource.Success -> Resource.Success(Unit)
            is Resource.Error -> resp
        }
    }

    override suspend fun updateCategory(id: Int, name: String, color: Color): Resource<Unit> {
        val request = UpdateCategoryRequest(
            id = id,
            name = name,
            colorCategory = CategoryColorMapper.fromUiColor(color)
        )
        return when (val resp = webClient.updateCategory(request)) {
            is Resource.Success -> Resource.Success(Unit)
            is Resource.Error -> resp
        }
    }

    override suspend fun deleteCategory(id: Int): Resource<Unit> {
        return when (val resp = webClient.deleteCategory(id)) {
            is Resource.Success -> Resource.Success(Unit)
            is Resource.Error -> resp
        }
    }

}
