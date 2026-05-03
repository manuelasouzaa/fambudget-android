package br.com.manuelasouzaa.fambudget.feature.category.data.repository

import br.com.manuelasouzaa.fambudget.core.network.resource.Resource
import br.com.manuelasouzaa.fambudget.feature.category.data.mapper.CategoryMapper.toCategorySections
import br.com.manuelasouzaa.fambudget.feature.category.data.remote.CategoryWebClient
import br.com.manuelasouzaa.fambudget.feature.category.domain.CategoryRepository
import br.com.manuelasouzaa.fambudget.feature.category.ui.model.CategorySections

class CategoryRepositoryImpl(private val webClient: CategoryWebClient) : CategoryRepository {

    override suspend fun getUserCategories(): Resource<CategorySections> {
        return when (val resp = webClient.getUserCategories()) {
            is Resource.Success -> Resource.Success(resp.data.toCategorySections())
            is Resource.Error -> resp
        }
    }

}
