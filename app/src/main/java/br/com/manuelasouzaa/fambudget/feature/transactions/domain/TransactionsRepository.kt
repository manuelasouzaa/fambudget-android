package br.com.manuelasouzaa.fambudget.feature.transactions.domain

import br.com.manuelasouzaa.fambudget.core.network.resource.Resource
import br.com.manuelasouzaa.fambudget.feature.transactions.ui.model.TransactionGroup

interface TransactionsRepository {
    suspend fun getTransactions(month: Int, year: Int): Resource<List<TransactionGroup>>
}
