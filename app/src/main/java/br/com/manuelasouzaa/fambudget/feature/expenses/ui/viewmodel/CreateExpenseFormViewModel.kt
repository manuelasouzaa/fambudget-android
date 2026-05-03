package br.com.manuelasouzaa.fambudget.feature.expenses.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.manuelasouzaa.fambudget.R
import br.com.manuelasouzaa.fambudget.core.network.resource.Resource
import br.com.manuelasouzaa.fambudget.core.ui.model.FormEvent
import br.com.manuelasouzaa.fambudget.feature.category.domain.CategoryRepository
import br.com.manuelasouzaa.fambudget.feature.category.ui.model.CategorySections
import br.com.manuelasouzaa.fambudget.feature.category.ui.model.CategoryUiModel
import br.com.manuelasouzaa.fambudget.feature.expenses.domain.ExpensesRepository
import br.com.manuelasouzaa.fambudget.feature.expenses.ui.model.ExpenseType
import br.com.manuelasouzaa.fambudget.feature.expenses.ui.model.PaymentType
import br.com.manuelasouzaa.fambudget.feature.expenses.ui.uistate.CreateExpenseFormUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CreateExpenseFormViewModel(
    private val expensesRepository: ExpensesRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    init {
        getUserCategories()
    }

    private val _uiState = MutableStateFlow(CreateExpenseFormUiState())
    val uiState: StateFlow<CreateExpenseFormUiState> = _uiState.asStateFlow()

    private val _categories = MutableStateFlow(CategorySections())
    val categories = _categories.asStateFlow()

    fun onExpenseNameChange(name: String) {
        _uiState.value = uiState.value.copy(name = name)
    }

    fun onExpenseCategoryChange(category: CategoryUiModel) {
        _uiState.value = uiState.value.copy(category = category)
    }

    fun onExpenseTypeChange(expenseType: ExpenseType) {
        _uiState.value = uiState.value.copy(expenseType = expenseType)
    }

    fun onExpenseDescriptionChange(description: String) {
        _uiState.value = uiState.value.copy(description = description)
    }

    fun onExpenseValueChange(value: String) {
        _uiState.value = uiState.value.copy(value = value)
    }

    fun onExpenseDateChange(date: String) {
        _uiState.value = uiState.value.copy(initialDate = date)
    }

    fun onExpensePaidStateChange(isExpensePaid: Boolean) {
        _uiState.value = uiState.value.copy(isExpensePaid = isExpensePaid)
    }

    fun onExpensePaymentDateChange(date: String) {
        _uiState.value = uiState.value.copy(paymentDate = date)
    }

    fun onPaymentTypeChange(paymentType: PaymentType) {
        _uiState.value = uiState.value.copy(paymentType = paymentType)
    }

    fun saveExpense() {
        val state = uiState.value
        if (state.name.isBlank() || state.category == null || state.value.isBlank() || state.value == "0" || state.initialDate.isBlank()) {
            _uiState.value = state.copy(event = FormEvent.Error(message = R.string.error_empty_fields, navigateBack = false))
            return
        }
        viewModelScope.launch {
            when (expensesRepository.saveExpense(
                name = state.name,
                categoryId = state.category.id,
                value = state.value,
                initialDate = state.initialDate,
                isExpensePaid = state.isExpensePaid,
                paymentDate = state.paymentDate,
                description = state.description,
                expenseType = state.expenseType.id,
                typePaymentId = state.paymentType.id
            )) {
                is Resource.Success -> _uiState.value = state.copy(event = FormEvent.Success(R.string.success_expense_created))
                is Resource.Error -> _uiState.value = state.copy(event = FormEvent.Error(R.string.error_unknown, navigateBack = false))
            }
        }
    }

    private fun getUserCategories() {
        viewModelScope.launch {
            when (val result = categoryRepository.getUserCategories()) {
                is Resource.Success -> {
                    _categories.value = result.data
                    _uiState.value = uiState.value.copy(category = result.data.default[0])
                }
                is Resource.Error -> _uiState.value = uiState.value.copy(
                    event = FormEvent.Error(message = R.string.error_unknown, navigateBack = true)
                )
            }
        }
    }
}
