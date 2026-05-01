package br.com.manuelasouzaa.fambudget.feature.expenses.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview
import br.com.manuelasouzaa.fambudget.core.ui.theme.FamBudgetTheme

@Composable
fun AddExpenseFormScreen(modifier: Modifier = Modifier) {

    AddExpenseFormContent(modifier = modifier)

}

@Composable
fun AddExpenseFormContent(modifier: Modifier = Modifier) {

}

@Preview
@Composable
private fun AddExpenseFormScreenPreview() {
    FamBudgetTheme {
        Surface {
            AddExpenseFormContent(Modifier.fillMaxSize())
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun AddExpenseFormScreenDarkThemePreview() {
    FamBudgetTheme {
        Surface {
            AddExpenseFormContent(Modifier.fillMaxSize())
        }
    }
}
