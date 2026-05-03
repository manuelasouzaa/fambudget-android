package br.com.manuelasouzaa.fambudget.feature.category.data.remote

import br.com.manuelasouzaa.fambudget.feature.category.data.remote.model.CategoryListResponse
import retrofit2.Response
import retrofit2.http.GET

interface CategoryService {

    @GET("v1/category/user")
    suspend fun getUserCategories(): Response<CategoryListResponse>

}
