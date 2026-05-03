package br.com.manuelasouzaa.fambudget.feature.category.data.remote.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class CategoryListResponse(
    @field:JsonProperty("default")
    val default: List<CategoryResponse>,

    @field:JsonProperty("user")
    val user: List<CategoryResponse>
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class CategoryResponse(
    @field:JsonProperty("id")
    val id: Int,

    @field:JsonProperty("name")
    val name: String,

    @field:JsonProperty("color")
    val color: String
)
