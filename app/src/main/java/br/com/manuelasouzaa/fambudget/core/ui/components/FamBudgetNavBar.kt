package br.com.manuelasouzaa.fambudget.core.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.manuelasouzaa.fambudget.R
import br.com.manuelasouzaa.fambudget.core.ui.navigation.ScreenDestinations

@Composable
fun FamBudgetNavBar(
    modifier: Modifier = Modifier,
    currentRoute: String?,
    onDestinationClick: (String) -> Unit
) {
    val items = listOf(
        BottomNavBarItem(ScreenDestinations.HomeScreen, R.string.home, R.drawable.ic_home),
        BottomNavBarItem(
            ScreenDestinations.TransactionsScreen,
            R.string.transactions,
            R.drawable.ic_transactions
        ),
        BottomNavBarItem(ScreenDestinations.BudgetsScreen, R.string.budgets, R.drawable.ic_budgets),
        BottomNavBarItem(ScreenDestinations.ReportsScreen, R.string.reports, R.drawable.ic_reports)
    )

    Column {
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            1.dp,
            color = MaterialTheme.colorScheme.tertiary
        )
        NavigationBar(
            modifier = modifier.fillMaxWidth()
        ) {
            items.forEach { (destination, title, icon) ->
                val isSelected = currentRoute == destination.name

                NavigationBarItem(
                    selected = isSelected,
                    onClick = {
                        onDestinationClick(destination.name)
                    },
                    icon = {
                        Icon(
                            painter = painterResource(icon),
                            contentDescription = stringResource(title),
                            tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier.size(40.dp)
                        )
                    },
                )
            }
        }
    }
}

private data class BottomNavBarItem(
    val destination: ScreenDestinations,
    @field:StringRes val title: Int,
    @field:DrawableRes val icon: Int
)
