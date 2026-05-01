@file:OptIn(ExperimentalMaterial3Api::class)

package br.com.manuelasouzaa.fambudget.feature.home.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.manuelasouzaa.fambudget.R
import br.com.manuelasouzaa.fambudget.core.ui.theme.FamBudgetTheme
import br.com.manuelasouzaa.fambudget.feature.home.di.modules.homeModule
import br.com.manuelasouzaa.fambudget.feature.home.ui.components.HomeScreenCardView
import br.com.manuelasouzaa.fambudget.feature.home.ui.uistate.HomeUiStateData
import br.com.manuelasouzaa.fambudget.feature.home.ui.viewmodel.HomeUiState
import br.com.manuelasouzaa.fambudget.feature.home.ui.viewmodel.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigateToNewExpense: () -> Unit = {},
    onNavigateToNewIncome: () -> Unit = {}
) {
    val viewModel: HomeViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()

    var isRefreshing by remember { mutableStateOf(false) }

    LaunchedEffect(uiState) {
        if (uiState is HomeUiState.Success)
            isRefreshing = false
    }

    fun refresh() {
        isRefreshing = true
        viewModel.refresh()
    }

    when (val uiState = uiState) {
        HomeUiState.Error -> {}

        HomeUiState.Loading -> {
            if (!isRefreshing)
                Column(
                    Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
        }

        is HomeUiState.Success -> {
            HomeContent(
                modifier = modifier,
                uiState = uiState.uiStateData,
                isRefreshing = isRefreshing,
                onRefresh = ::refresh,
                onNavigateToNewExpense = onNavigateToNewExpense,
                onNavigateToNewIncome = onNavigateToNewIncome
            )
        }
    }
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    uiState: HomeUiStateData,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onNavigateToNewExpense: () -> Unit = {},
    onNavigateToNewIncome: () -> Unit = {},
) {
    val isCurrentBalancePositive = uiState.isCurrentBalancePositive
    var isFabExpanded by remember { mutableStateOf(false) }
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = { onRefresh() },
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(20.dp).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(36.dp)
        ) {
            Text(
                text = stringResource(R.string.home_screen_hello_user, uiState.userName),
                style = MaterialTheme.typography.titleLarge,
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = stringResource(R.string.current_balance),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.tertiary
                )
                Text(
                    text = uiState.currentBalance,
                    style = MaterialTheme.typography.headlineLarge
                )
                Row(
                    modifier = Modifier.wrapContentSize(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val stringRes = if (isCurrentBalancePositive)
                        R.string.positive_balance
                    else
                        R.string.negative_balance

                    val color = if (isCurrentBalancePositive)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.error

                    val icon =
                        if (isCurrentBalancePositive) R.drawable.ic_positive else R.drawable.ic_negative

                    Icon(
                        painter = painterResource(icon),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = color
                    )

                    Text(
                        text = stringResource(stringRes),
                        color = color,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                HomeScreenCardView(
                    modifier = Modifier,
                    value = uiState.expensesTotal,
                    isExpenses = true
                )

                HomeScreenCardView(
                    modifier = Modifier,
                    value = uiState.incomeTotal,
                    isExpenses = false
                )
            }
        }
    }

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AnimatedVisibility(
                visible = isFabExpanded,
                enter = fadeIn() + slideInVertically { it },
                exit = fadeOut() + slideOutVertically { it }
            ) {
                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FabMenuItem(
                        text = stringResource(R.string.new_expense),
                        onClick = {
                            isFabExpanded = false
                            onNavigateToNewExpense()
                        }
                    )
                    FabMenuItem(
                        text = stringResource(R.string.new_income),
                        onClick = {
                            isFabExpanded = false
                            onNavigateToNewIncome()
                        }
                    )
                }
            }

            FloatingActionButton(
                onClick = { isFabExpanded = !isFabExpanded },
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_add),
                    contentDescription = null,
                    modifier = Modifier.rotate(if (isFabExpanded) 45f else 0f)
                )
            }
        }
    }
}

@Composable
private fun FabMenuItem(text: String, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        modifier = Modifier.clickable { onClick() }
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    FamBudgetTheme {
        Surface {
            HomeContent(
                uiState = HomeUiStateData(
                    userName = "User",
                    currentBalance = "R$ -1.500,00",
                    isCurrentBalancePositive = false,
                    expensesTotal = "R$ 3.500,00",
                    incomeTotal = "R$ 2.000,00",
                ),
                isRefreshing = true,
                onRefresh = {}
            )
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun HomeScreenDarkThemePreview() {
    FamBudgetTheme {
        Surface {
            HomeContent(
                uiState = HomeUiStateData(
                    userName = "User",
                    currentBalance = "2.000,00",
                    isCurrentBalancePositive = true,
                    expensesTotal = "R$ 1.500,00",
                    incomeTotal = "R$ 3.500,00",
                ),
                isRefreshing = false,
                onRefresh = {}
            )
        }
    }
}
