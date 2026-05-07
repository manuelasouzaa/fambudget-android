package br.com.manuelasouzaa.fambudget.feature.transactions.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.manuelasouzaa.fambudget.R
import br.com.manuelasouzaa.fambudget.core.ui.components.FamBudgetMonthSelector
import br.com.manuelasouzaa.fambudget.core.ui.theme.primary
import br.com.manuelasouzaa.fambudget.feature.transactions.ui.model.TransactionGroup
import br.com.manuelasouzaa.fambudget.feature.transactions.ui.model.TransactionItem
import br.com.manuelasouzaa.fambudget.feature.transactions.ui.viewmodel.TransactionsUiState
import br.com.manuelasouzaa.fambudget.feature.transactions.ui.viewmodel.TransactionsViewModel
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun TransactionsScreen(modifier: Modifier = Modifier) {
    val viewModel: TransactionsViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val currentMonth by viewModel.currentMonth.collectAsStateWithLifecycle()

    Column(modifier.fillMaxSize()) {
        val state = uiState
        val balance = if (state is TransactionsUiState.Success) state.balance else null
        TransactionsHeader(
            currentMonth = currentMonth,
            balance = balance,
            onPreviousMonth = viewModel::previousMonth,
            onNextMonth = viewModel::nextMonth,
            onMonthSelected = viewModel::jumpToMonth
        )
        when (state) {
            TransactionsUiState.Loading -> CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize()
            )

            TransactionsUiState.Error -> Text(
                text = stringResource(R.string.error_load_transactions),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            )

            is TransactionsUiState.Success -> {
                if (state.groups.isEmpty())
                    Text(
                        text = stringResource(R.string.no_transactions),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                else TransactionsList(state.groups)
            }
        }
    }
}

@Composable
private fun TransactionsHeader(
    currentMonth: YearMonth,
    balance: Double?,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit,
    onMonthSelected: (YearMonth) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FamBudgetMonthSelector(
            currentMonth = currentMonth,
            onPreviousMonth = onPreviousMonth,
            onNextMonth = onNextMonth,
            onMonthSelected = onMonthSelected
        )
        if (balance != null) {
            val isPositive = balance >= 0
            val balanceLabel = if (currentMonth == YearMonth.now()) {
                stringResource(R.string.current_balance)
            } else {
                val lastDay = currentMonth.atEndOfMonth()
                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                stringResource(R.string.balance_at, lastDay)
            }
            Card(
                modifier = Modifier.wrapContentSize(),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = balanceLabel,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = balance.toCurrencyString(),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = if (isPositive) primary else MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Composable
private fun TransactionsList(groups: List<TransactionGroup>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        groups.forEach { group ->
            item {
                Text(
                    text = group.date.toDisplayString(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
                )
                HorizontalDivider()
            }
            items(group.items) { item ->
                TransactionRow(item)
            }
        }
    }
}

@Composable
private fun TransactionRow(item: TransactionItem) {
    val (label, value, isIncome) = when (item) {
        is TransactionItem.Income -> Triple(
            item.description ?: stringResource(R.string.income_singular),
            "+${item.value.toCurrencyString()}",
            true
        )

        is TransactionItem.Expense -> Triple(item.name, "-${item.value.toCurrencyString()}", false)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyLarge)
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            color = if (isIncome) primary else MaterialTheme.colorScheme.error
        )
    }
}

private fun LocalDate.toDisplayString(): String {
    val dayMonth = DateTimeFormatter.ofPattern("dd 'de' MMMM", Locale("pt", "BR")).format(this)
    val weekDay = DateTimeFormatter.ofPattern("EEEE", Locale("pt", "BR")).format(this)
    return "$dayMonth, $weekDay"
}

private fun Double.toCurrencyString(): String =
    String.format(Locale("pt", "BR"), "R$ %.2f", this)
