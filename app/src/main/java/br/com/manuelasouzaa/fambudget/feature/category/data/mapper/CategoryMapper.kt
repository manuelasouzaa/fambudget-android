package br.com.manuelasouzaa.fambudget.feature.category.data.mapper

import br.com.manuelasouzaa.fambudget.feature.category.data.remote.model.CategoryListResponse
import br.com.manuelasouzaa.fambudget.feature.category.data.remote.model.CategoryResponse
import br.com.manuelasouzaa.fambudget.feature.category.ui.model.CategorySections
import br.com.manuelasouzaa.fambudget.feature.category.ui.model.CategoryUiModel

object CategoryMapper {

    fun CategoryResponse.toUiModel(): CategoryUiModel {
        return CategoryUiModel(
            id = this.id,
            name = this.name,
            color = CategoryColorMapper.fromValue(this.color).toUiColor()
        )
    }

    fun CategoryUiModel.toNetworkModel(): CategoryResponse {
        return CategoryResponse(
            id = this.id,
            name = this.name,
            color = CategoryColorMapper.fromUiColor(this.color)
        )
    }

    fun CategoryListResponse.toCategorySections(): CategorySections {
        return CategorySections(
            default = this.default.map { it.toUiModel() },
            user = this.user.map { it.toUiModel() }
        )
    }

}
