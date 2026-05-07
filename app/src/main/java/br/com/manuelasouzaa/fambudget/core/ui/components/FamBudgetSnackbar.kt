package br.com.manuelasouzaa.fambudget.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.manuelasouzaa.fambudget.core.ui.model.SnackbarType

@Composable
fun FamBudgetSnackbar(
    snackbarHostState: SnackbarHostState,
    snackbarType: SnackbarType,
    modifier: Modifier = Modifier
) {
    val containerColor = when (snackbarType) {
        SnackbarType.ERROR -> MaterialTheme.colorScheme.errorContainer
        SnackbarType.INFO -> MaterialTheme.colorScheme.primaryContainer
    }

    val contentColor = when (snackbarType) {
        SnackbarType.ERROR -> MaterialTheme.colorScheme.onErrorContainer
        SnackbarType.INFO -> MaterialTheme.colorScheme.onPrimaryContainer
    }

    SnackbarHost(
        hostState = snackbarHostState,
        modifier = modifier
            .navigationBarsPadding()
            .padding(16.dp)
    ) { data ->
        Snackbar(
            modifier = Modifier.fillMaxWidth(),
            containerColor = containerColor,
            contentColor = contentColor,
            shape = RoundedCornerShape(5.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(4.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = data.visuals.message,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.wrapContentSize()
                )
            }
        }
    }
}
