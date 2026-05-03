package br.com.manuelasouzaa.fambudget.feature.category.data.remote

import br.com.manuelasouzaa.fambudget.core.network.client.BaseHttpClient
import br.com.manuelasouzaa.fambudget.core.network.resource.Resource
import br.com.manuelasouzaa.fambudget.feature.category.data.remote.model.CategoryListResponse

class CategoryWebClient(private val service: CategoryService) : BaseHttpClient() {

    suspend fun getUserCategories(): Resource<CategoryListResponse> {
        return safeApiCall { service.getUserCategories() }
    }

}
