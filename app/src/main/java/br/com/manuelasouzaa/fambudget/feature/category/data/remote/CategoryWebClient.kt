package br.com.manuelasouzaa.fambudget.feature.category.data.remote

import br.com.manuelasouzaa.fambudget.core.network.client.BaseHttpClient
import br.com.manuelasouzaa.fambudget.core.network.resource.Resource
import br.com.manuelasouzaa.fambudget.feature.category.data.remote.model.CategoryListResponse
import br.com.manuelasouzaa.fambudget.feature.category.data.remote.model.CreateCategoryRequest
import br.com.manuelasouzaa.fambudget.feature.category.data.remote.model.UpdateCategoryRequest

class CategoryWebClient(private val service: CategoryService) : BaseHttpClient() {

    suspend fun getUserCategories(): Resource<CategoryListResponse> {
        return safeApiCall { service.getUserCategories() }
    }

    suspend fun createCategory(request: CreateCategoryRequest): Resource<Unit> {
        return safeApiCall { service.createCategory(request) }
    }

    suspend fun updateCategory(request: UpdateCategoryRequest): Resource<Unit> {
        return safeApiCall { service.updateCategory(request) }
    }

    suspend fun deleteCategory(categoryId: Int): Resource<Unit> {
        return safeApiCall { service.deleteCategory(categoryId) }
    }

}
