package br.com.manuelasouzaa.fambudget.core.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.manuelasouzaa.fambudget.R

@Composable
fun FamBudgetErrorScreen(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    @StringRes messageRes: Int = R.string.error_unknown
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(messageRes),
            style = MaterialTheme.typography.bodyLarge
        )
        Button(
            onClick = onRetry,
            modifier = Modifier.padding(top = 16.dp),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(
                text = stringResource(R.string.retry),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
