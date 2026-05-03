package br.com.manuelasouzaa.fambudget.feature.category.domain

import br.com.manuelasouzaa.fambudget.core.network.resource.Resource
import br.com.manuelasouzaa.fambudget.feature.category.ui.model.CategorySections

interface CategoryRepository {

    suspend fun getUserCategories(): Resource<CategorySections>

}
