package br.com.manuelasouzaa.fambudget.feature.income.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.manuelasouzaa.fambudget.R
import br.com.manuelasouzaa.fambudget.core.ui.components.FamBudgetDatePicker
import br.com.manuelasouzaa.fambudget.core.ui.model.FormEvent
import br.com.manuelasouzaa.fambudget.core.ui.model.SnackbarType
import br.com.manuelasouzaa.fambudget.ext.toCurrencyDisplay
import br.com.manuelasouzaa.fambudget.feature.income.ui.uistate.AddIncomeFormUiState
import br.com.manuelasouzaa.fambudget.feature.income.ui.viewmodel.AddIncomeFormViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddIncomeFormScreen(
    modifier: Modifier = Modifier,
    onShowSnackbar: (String, SnackbarType) -> Unit,
    onNavigateBack: () -> Unit
) {
    val viewModel: AddIncomeFormViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
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

    AddIncomeFormContent(
        modifier = modifier,
        uiState = uiState,
        onValueChange = viewModel::onValueChange,
        onDateChange = viewModel::onDateChange,
        onDescriptionChange = viewModel::onDescriptionChange,
        onSaveClick = viewModel::saveIncome,
        onNavigateBack = onNavigateBack
    )
}

@Composable
fun AddIncomeFormContent(
    modifier: Modifier = Modifier,
    uiState: AddIncomeFormUiState,
    onValueChange: (String) -> Unit,
    onDateChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
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
                stringResource(
                    if (uiState.isEditMode) R.string.edit_income_title else R.string.create_income
                ), style = MaterialTheme.typography.titleLarge
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
            Text(text = stringResource(R.string.value), style = MaterialTheme.typography.bodyLarge)

            val displayValue = uiState.value.toCurrencyDisplay()
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = TextFieldValue(
                    text = displayValue,
                    selection = TextRange(displayValue.length)
                ),
                onValueChange = { onValueChange(it.text.filter { c -> c.isDigit() }) },
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number
                ),
                shape = RoundedCornerShape(10.dp)
            )

            Text(text = stringResource(R.string.date), style = MaterialTheme.typography.bodyLarge)

            FamBudgetDatePicker(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.dateInitial,
                onDateSelected = onDateChange
            )

            Text(
                text = stringResource(R.string.description_optional),
                style = MaterialTheme.typography.bodyLarge
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.description,
                onValueChange = onDescriptionChange,
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Text
                ),
                shape = RoundedCornerShape(10.dp)
            )

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
