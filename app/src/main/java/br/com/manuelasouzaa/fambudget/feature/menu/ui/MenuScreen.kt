package br.com.manuelasouzaa.fambudget.feature.menu.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.manuelasouzaa.fambudget.R
import br.com.manuelasouzaa.fambudget.core.ui.navigation.ScreenDestinations
import br.com.manuelasouzaa.fambudget.core.ui.theme.FamBudgetTheme

private sealed class MenuIcon {
    data class Res(@field:DrawableRes val id: Int) : MenuIcon()
    data class Vector(val icon: ImageVector) : MenuIcon()
}

private data class MenuItem(
    val label: String,
    val icon: MenuIcon,
    val destination: ScreenDestinations
)

@Composable
fun MenuScreen(
    modifier: Modifier = Modifier,
    onNavigate: (ScreenDestinations) -> Unit
) {
    val items = listOf(
        MenuItem(
            stringResource(R.string.user_profile),
            MenuIcon.Res(R.drawable.ic_user),
            ScreenDestinations.UserProfileScreen
        ),
        MenuItem(
            stringResource(R.string.income),
            MenuIcon.Res(R.drawable.ic_income),
            ScreenDestinations.IncomeScreen
        ),
        MenuItem(
            stringResource(R.string.transactions),
            MenuIcon.Res(R.drawable.ic_transactions),
            ScreenDestinations.TransactionsScreen
        ),
        MenuItem(
            stringResource(R.string.reports),
            MenuIcon.Res(R.drawable.ic_reports),
            ScreenDestinations.ReportsScreen
        ),
        MenuItem(
            stringResource(R.string.expenses),
            MenuIcon.Res(R.drawable.ic_expenses),
            ScreenDestinations.ExpensesScreen
        ),
        MenuItem(
            stringResource(R.string.pending_expenses),
            MenuIcon.Vector(Icons.Default.HourglassEmpty),
            ScreenDestinations.PendingExpensesScreen
        ),
        MenuItem(
            stringResource(R.string.paid_expenses),
            MenuIcon.Vector(Icons.Default.TaskAlt),
            ScreenDestinations.PaidExpensesScreen
        ),
        MenuItem(
            stringResource(R.string.family),
            MenuIcon.Vector(Icons.Default.Groups),
            ScreenDestinations.FamilyScreen
        ),
        MenuItem(
            stringResource(R.string.categories_title),
            MenuIcon.Res(R.drawable.ic_budgets),
            ScreenDestinations.CategoriesScreen
        ),
    )

    MenuContent(
        modifier = modifier,
        items = items,
        onNavigate = onNavigate
    )
}

@Composable
private fun MenuContent(
    modifier: Modifier = Modifier,
    items: List<MenuItem>,
    onNavigate: (ScreenDestinations) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(20.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(items) { item ->
                MenuItemCard(item = item, onNavigate = onNavigate)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MenuItemCard(item: MenuItem, onNavigate: (ScreenDestinations) -> Unit) {
    val cardColor = MaterialTheme.colorScheme.surfaceContainerLowest

    Card(
        onClick = { onNavigate(item.destination) },
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 90.dp, max = 110.dp),
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardColor,
            contentColor = MaterialTheme.colorScheme.onBackground
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp,
        ),
        border = BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            when (val icon = item.icon) {
                is MenuIcon.Res -> Icon(
                    painter = painterResource(icon.id),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(30.dp)
                )

                is MenuIcon.Vector -> Icon(
                    imageVector = icon.icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(30.dp)
                )
            }

            Text(
                text = item.label,
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(8.dp),
                maxLines = 2
            )
        }
    }
}

@Preview
@Composable
private fun MenuScreenPreview() {
    FamBudgetTheme {
        Surface {
            MenuScreen {}
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun MenuScreenDarkPreview() {
    FamBudgetTheme {
        Surface {
            MenuScreen {}
        }
    }
}
