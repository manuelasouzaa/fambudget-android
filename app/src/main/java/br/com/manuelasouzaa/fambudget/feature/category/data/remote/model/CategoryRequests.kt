package br.com.manuelasouzaa.fambudget.feature.category.data.remote.model

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateCategoryRequest(
    @field:JsonProperty("name")
    val name: String,

    @field:JsonProperty("colorCategory")
    val colorCategory: String
)

data class UpdateCategoryRequest(
    @field:JsonProperty("id")
    val id: Int,

    @field:JsonProperty("name")
    val name: String,

    @field:JsonProperty("colorCategory")
    val colorCategory: String
)
