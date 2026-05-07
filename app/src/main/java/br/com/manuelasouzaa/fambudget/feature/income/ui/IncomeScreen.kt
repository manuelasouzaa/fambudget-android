package br.com.manuelasouzaa.fambudget.feature.income.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.manuelasouzaa.fambudget.R
import br.com.manuelasouzaa.fambudget.core.ui.components.FamBudgetErrorScreen
import br.com.manuelasouzaa.fambudget.core.ui.components.FamBudgetMonthSelector
import br.com.manuelasouzaa.fambudget.core.ui.theme.FamBudgetTheme
import br.com.manuelasouzaa.fambudget.ext.toCurrency
import br.com.manuelasouzaa.fambudget.feature.income.data.remote.model.IncomeResponse
import br.com.manuelasouzaa.fambudget.feature.income.ui.uistate.IncomeScreenUiState
import br.com.manuelasouzaa.fambudget.feature.income.ui.viewmodel.IncomeScreenViewModel
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@Composable
fun IncomeScreen(
    modifier: Modifier = Modifier,
    refreshTrigger: Lifecycle.State = Lifecycle.State.RESUMED,
    onNavigateToNewIncome: () -> Unit = {},
    onNavigateToEditIncome: (IncomeResponse) -> Unit = {}
) {
    val viewModel: IncomeScreenViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val currentMonth by viewModel.currentMonth.collectAsStateWithLifecycle()

    var incomeToDelete by remember { mutableStateOf<IncomeResponse?>(null) }
    val showDeleteDialog = incomeToDelete != null

    fun dismissDeleteDialog() {
        incomeToDelete = null
    }

    LaunchedEffect(refreshTrigger) {
        if (refreshTrigger == Lifecycle.State.RESUMED) viewModel.load()
    }

    Box(modifier = modifier.fillMaxSize()) {
        IncomeContent(
            uiState = uiState,
            currentMonth = currentMonth,
            onPreviousMonth = viewModel::previousMonth,
            onNextMonth = viewModel::nextMonth,
            onMonthSelected = viewModel::jumpToMonth,
            onEdit = onNavigateToEditIncome,
            onDelete = { incomeToDelete = it },
            onNavigateToNewIncome = onNavigateToNewIncome,
            onRetry = viewModel::load
        )
    }

    if (showDeleteDialog) {
        DeleteConfirmationDialog(
            onConfirm = {
                incomeToDelete?.let { viewModel.deleteIncome(it.id) }
                dismissDeleteDialog()
            },
            onDismiss = ::dismissDeleteDialog
        )
    }
}


@Composable
private fun TotalCard(total: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.total),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = total,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun IncomeList(
    incomes: List<IncomeResponse>,
    onEdit: (IncomeResponse) -> Unit,
    onDelete: (IncomeResponse) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(incomes, key = { it.id }) { income ->
            IncomeItem(
                income = income,
                onEdit = { onEdit(income) },
                onDelete = { onDelete(income) })
            HorizontalDivider()
        }
    }
}

@Composable
private fun IncomeItem(
    income: IncomeResponse,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = income.description ?: stringResource(R.string.income_singular),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            income.dateInitial?.let {
                Text(
                    text = it.formatToDisplay(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        }
        Text(
            text = income.value.toCurrency(),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(end = 8.dp)
        )
        IconButton(onClick = onEdit) {
            Icon(
                Icons.Default.Edit,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
        IconButton(onClick = onDelete) {
            Icon(
                Icons.Default.Delete,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Composable
private fun DeleteConfirmationDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.delete_income_title)) },
        text = { Text(stringResource(R.string.delete_income_message)) },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(stringResource(R.string.confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}

private fun String.formatToDisplay(): String {
    return try {
        val parsed = LocalDate.parse(this, DateTimeFormatter.ISO_LOCAL_DATE)
        parsed.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    } catch (_: Exception) {
        this
    }
}

@Composable
private fun IncomeContent(
    modifier: Modifier = Modifier,
    uiState: IncomeScreenUiState,
    currentMonth: YearMonth = YearMonth.now(),
    onPreviousMonth: () -> Unit = {},
    onNextMonth: () -> Unit = {},
    onMonthSelected: (YearMonth) -> Unit = {},
    onEdit: (IncomeResponse) -> Unit = {},
    onDelete: (IncomeResponse) -> Unit = {},
    onNavigateToNewIncome: () -> Unit = {},
    onRetry: () -> Unit = {}
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            FamBudgetMonthSelector(
                currentMonth = currentMonth,
                onPreviousMonth = onPreviousMonth,
                onNextMonth = onNextMonth,
                onMonthSelected = onMonthSelected
            )

            when (uiState) {
                IncomeScreenUiState.Loading -> CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize()
                )

                is IncomeScreenUiState.Error -> FamBudgetErrorScreen(
                    onRetry = onRetry,
                    messageRes = uiState.messageRes
                )

                is IncomeScreenUiState.Success -> {
                    if (uiState.incomes.isEmpty()) {
                        Text(
                            text = stringResource(R.string.no_incomes),
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    } else {
                        TotalCard(uiState.total)
                        IncomeList(
                            incomes = uiState.incomes,
                            onEdit = onEdit,
                            onDelete = onDelete
                        )
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = onNavigateToNewIncome,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp),
            shape = CircleShape,
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_add),
                contentDescription = null,
            )
        }
    }
}

@Preview
@Composable
private fun IncomeScreenPreview() {
    FamBudgetTheme {
        Surface {
            IncomeContent(
                uiState = IncomeScreenUiState.Success(
                    incomes = listOf(
                        IncomeResponse(1, 1, "Salário", 5000.0, "2025-01-15", null),
                        IncomeResponse(2, 1, "Freelance", 1200.0, "2025-01-20", null)
                    ),
                    total = "R$ 6.200,00"
                )
            )
        }
    }
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun IncomeScreenDarkPreview() {
    FamBudgetTheme {
        Surface {
            IncomeContent(
                uiState = IncomeScreenUiState.Success(
                    incomes = listOf(
                        IncomeResponse(1, 1, "Salário", 5000.0, "2025-01-15", null),
                        IncomeResponse(2, 1, "Freelance", 1200.0, "2025-01-20", null)
                    ),
                    total = "R$ 6.200,00"
                )
            )
        }
    }
}

@Preview
@Composable
private fun IncomeScreenEmptyPreview() {
    FamBudgetTheme {
        IncomeContent(
            uiState = IncomeScreenUiState.Success(
                incomes = emptyList(),
                total = "R$ 0,00"
            )
        )
    }
}
