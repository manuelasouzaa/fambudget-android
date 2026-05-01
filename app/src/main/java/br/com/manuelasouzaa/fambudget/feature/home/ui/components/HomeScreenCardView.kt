package br.com.manuelasouzaa.fambudget.feature.home.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.manuelasouzaa.fambudget.R

@Composable
fun RowScope.HomeScreenCardView(modifier: Modifier = Modifier, value: String, isExpenses: Boolean) {
    val icon = if (isExpenses) R.drawable.ic_expenses else R.drawable.ic_income
    val title = if (isExpenses) R.string.expenses else R.string.income
    val backgroundColor =
        if (isExpenses) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.primary
    val textColor =
        if (isExpenses) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onPrimary

    Card(
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        modifier = modifier
            .fillMaxWidth()
            .weight(1f),
    ) {
        Row(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
            Icon(
                painter = painterResource(icon),
                contentDescription = stringResource(title),
                modifier = Modifier.padding(end = 8.dp),
                tint = textColor
            )
            Text(
                text = stringResource(title),
                color = textColor,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Text(
            text = value,
            color = textColor,
            modifier = Modifier.padding(bottom = 8.dp, start = 12.dp, end = 12.dp),
            style = MaterialTheme.typography.titleLarge
        )
    }
}
