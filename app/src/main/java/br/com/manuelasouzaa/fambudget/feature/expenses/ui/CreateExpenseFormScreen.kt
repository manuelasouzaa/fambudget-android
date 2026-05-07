package br.com.manuelasouzaa.fambudget.feature.expenses.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.manuelasouzaa.fambudget.R
import br.com.manuelasouzaa.fambudget.core.ui.components.FamBudgetDatePicker
import br.com.manuelasouzaa.fambudget.core.ui.model.FormEvent
import br.com.manuelasouzaa.fambudget.core.ui.model.SnackbarType
import br.com.manuelasouzaa.fambudget.core.ui.theme.FamBudgetTheme
import br.com.manuelasouzaa.fambudget.core.ui.theme.categoryBlue
import br.com.manuelasouzaa.fambudget.core.ui.theme.categoryOrange
import br.com.manuelasouzaa.fambudget.core.ui.theme.categoryPink
import br.com.manuelasouzaa.fambudget.ext.toCurrencyDisplay
import br.com.manuelasouzaa.fambudget.feature.category.ui.model.CategorySections
import br.com.manuelasouzaa.fambudget.feature.category.ui.model.CategoryUiModel
import br.com.manuelasouzaa.fambudget.feature.expenses.ui.components.CategoryDropDownMenu
import br.com.manuelasouzaa.fambudget.feature.expenses.ui.components.ExpenseTypeButtonsRow
import br.com.manuelasouzaa.fambudget.feature.expenses.ui.components.PaymentTypeDropDownMenu
import br.com.manuelasouzaa.fambudget.feature.expenses.ui.model.ExpenseType
import br.com.manuelasouzaa.fambudget.feature.expenses.ui.model.PaymentType
import br.com.manuelasouzaa.fambudget.feature.expenses.ui.uistate.CreateExpenseFormUiState
import br.com.manuelasouzaa.fambudget.feature.expenses.ui.viewmodel.CreateExpenseFormViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreateExpenseFormScreen(
    modifier: Modifier = Modifier,
    onShowSnackbar: (String, SnackbarType) -> Unit,
    onNavigateBack: () -> Unit
) {
    val viewModel: CreateExpenseFormViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val categories by viewModel.categories.collectAsStateWithLifecycle()
    val snackbarMessage = uiState.event?.message?.let { stringResource(it) }

    LaunchedEffect(uiState.event) {
        uiState.event?.let { event ->
            snackbarMessage?.let { message ->
                when (event) {
                    is FormEvent.Error -> {
                        onShowSnackbar(message, SnackbarType.ERROR)
                        if (event.navigateBack) onNavigateBack()
                    }

                    is FormEvent.Success -> {
                        onShowSnackbar(message, SnackbarType.INFO)
                        onNavigateBack()
                    }
                }
            }
        }
    }

    CreateExpenseFormContent(
        modifier = modifier,
        uiState = uiState,
        categories = categories,
        onExpenseNameChange = viewModel::onExpenseNameChange,
        onExpenseCategoryChange = viewModel::onExpenseCategoryChange,
        onExpenseTypeChange = viewModel::onExpenseTypeChange,
        onExpenseDescriptionChange = viewModel::onExpenseDescriptionChange,
        onExpenseValueChange = viewModel::onExpenseValueChange,
        onExpenseDateChange = viewModel::onExpenseDateChange,
        onExpensePaidStateChange = viewModel::onExpensePaidStateChange,
        onExpensePaymentDateChange = viewModel::onExpensePaymentDateChange,
        onPaymentTypeChange = viewModel::onPaymentTypeChange,
        onSaveClick = viewModel::saveExpense,
        onNavigateBack = onNavigateBack
    )
}

@Composable
fun CreateExpenseFormContent(
    modifier: Modifier = Modifier,
    uiState: CreateExpenseFormUiState,
    categories: CategorySections,
    onExpenseNameChange: (String) -> Unit,
    onExpenseCategoryChange: (CategoryUiModel) -> Unit,
    onExpenseTypeChange: (ExpenseType) -> Unit,
    onExpenseDescriptionChange: (String) -> Unit,
    onExpenseValueChange: (String) -> Unit,
    onExpenseDateChange: (String) -> Unit,
    onExpensePaidStateChange: (Boolean) -> Unit,
    onExpensePaymentDateChange: (String) -> Unit,
    onPaymentTypeChange: (PaymentType) -> Unit,
    onSaveClick: () -> Unit,
    onNavigateBack: () -> Unit
) {
    Column(modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = onNavigateBack,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(imageVector = Icons.Default.ArrowBackIosNew, contentDescription = null)
            }
            Text(
                stringResource(R.string.create_expense),
                style = MaterialTheme.typography.titleLarge
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
                .imePadding(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = stringResource(R.string.name), style = MaterialTheme.typography.bodyLarge)

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.name,
                onValueChange = onExpenseNameChange,
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                ),
                shape = RoundedCornerShape(10.dp),
            )

            Text(
                text = stringResource(R.string.category),
                style = MaterialTheme.typography.bodyLarge
            )

            CategoryDropDownMenu(
                categories = categories,
                onCategorySelected = onExpenseCategoryChange
            )

            Text(
                text = stringResource(R.string.expense_type),
                style = MaterialTheme.typography.bodyLarge
            )

            ExpenseTypeButtonsRow(onExpenseTypeChange)

            Text(
                text = stringResource(R.string.description_optional),
                style = MaterialTheme.typography.bodyLarge
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.description,
                onValueChange = onExpenseDescriptionChange,
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                ),
                shape = RoundedCornerShape(10.dp),
            )

            Text(text = stringResource(R.string.value), style = MaterialTheme.typography.bodyLarge)

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.value.toCurrencyDisplay(),
                onValueChange = { onExpenseValueChange(it.filter { c -> c.isDigit() }) },
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number
                ),
                shape = RoundedCornerShape(10.dp),
            )

            Text(text = stringResource(R.string.date), style = MaterialTheme.typography.bodyLarge)

            FamBudgetDatePicker(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.initialDate,
                onDateSelected = onExpenseDateChange
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.is_expense_paid),
                    style = MaterialTheme.typography.bodyLarge
                )
                Checkbox(
                    checked = uiState.isExpensePaid,
                    onCheckedChange = { onExpensePaidStateChange(!uiState.isExpensePaid) }
                )
            }

            if (uiState.isExpensePaid)
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.date_payment),
                        style = MaterialTheme.typography.bodyLarge
                    )

                    FamBudgetDatePicker(
                        modifier = Modifier.fillMaxWidth(),
                        value = uiState.paymentDate,
                        maxDateMillis = System.currentTimeMillis(),
                        onDateSelected = onExpensePaymentDateChange
                    )

                    Text(
                        text = stringResource(R.string.payment_type),
                        style = MaterialTheme.typography.bodyLarge
                    )

                    PaymentTypeDropDownMenu(onPaymentTypeSelected = onPaymentTypeChange)
                }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                onClick = onSaveClick,
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = stringResource(R.string.save_title),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}

@Preview
@Composable
private fun CreateExpenseFormScreenPreview() {
    FamBudgetTheme {
        Surface {
            CreateExpenseFormContent(
                modifier = Modifier.fillMaxSize(),
                uiState = CreateExpenseFormUiState(isExpensePaid = true),
                categories = CategorySections(
                    default = listOf(
                        CategoryUiModel(id = 1, name = "Alimentação", color = categoryPink),
                        CategoryUiModel(id = 1, name = "Lazer", color = categoryBlue),
                        CategoryUiModel(id = 1, name = "Outros", color = categoryOrange),
                    )
                ),
                onExpenseNameChange = {},
                onExpenseCategoryChange = {},
                onExpenseTypeChange = {},
                onExpenseValueChange = {},
                onExpenseDateChange = {},
                onExpensePaidStateChange = {},
                onExpenseDescriptionChange = {},
                onExpensePaymentDateChange = {},
                onPaymentTypeChange = {},
                onSaveClick = {},
                onNavigateBack = {}
            )
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun CreateExpenseFormScreenDarkThemePreview() {
    FamBudgetTheme {
        Surface {
            CreateExpenseFormContent(
                modifier = Modifier.fillMaxSize(),
                uiState = CreateExpenseFormUiState(),
                categories = CategorySections(
                    default = listOf(
                        CategoryUiModel(id = 1, name = "Alimentação", color = categoryPink),
                        CategoryUiModel(id = 1, name = "Lazer", color = categoryBlue),
                        CategoryUiModel(id = 1, name = "Outros", color = categoryOrange),
                    )
                ),
                onExpenseNameChange = {},
                onExpenseCategoryChange = {},
                onExpenseTypeChange = {},
                onExpenseValueChange = {},
                onExpenseDateChange = {},
                onExpensePaidStateChange = {},
                onExpenseDescriptionChange = {},
                onExpensePaymentDateChange = {},
                onPaymentTypeChange = {},
                onSaveClick = {},
                onNavigateBack = {}
            )
        }
    }
}
