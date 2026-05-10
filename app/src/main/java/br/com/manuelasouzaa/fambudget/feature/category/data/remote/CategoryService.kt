package br.com.manuelasouzaa.fambudget.feature.category.data.remote

import br.com.manuelasouzaa.fambudget.feature.category.data.remote.model.CategoryListResponse
import br.com.manuelasouzaa.fambudget.feature.category.data.remote.model.CreateCategoryRequest
import br.com.manuelasouzaa.fambudget.feature.category.data.remote.model.UpdateCategoryRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CategoryService {

    @GET("v1/category/user")
    suspend fun getUserCategories(): Response<CategoryListResponse>

    @POST("v1/category")
    suspend fun createCategory(@Body request: CreateCategoryRequest): Response<Unit>

    @PUT("v1/category/update")
    suspend fun updateCategory(@Body request: UpdateCategoryRequest): Response<Unit>

    @DELETE("v1/category/{categoryId}")
    suspend fun deleteCategory(@Path("categoryId") categoryId: Int): Response<Unit>

}
