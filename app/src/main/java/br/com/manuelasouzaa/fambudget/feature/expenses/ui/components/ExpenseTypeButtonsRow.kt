package br.com.manuelasouzaa.fambudget.feature.expenses.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonColors
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import br.com.manuelasouzaa.fambudget.R
import br.com.manuelasouzaa.fambudget.feature.expenses.ui.model.ExpenseType

@Composable
fun ExpenseTypeButtonsRow(onExpenseTypeChange: (ExpenseType) -> Unit) {
    val options = listOf(
        ExpenseType.FIXED to R.string.expense_type_fixed,
        ExpenseType.VARIABLE to R.string.expense_type_variable,
        ExpenseType.SEASONAL to R.string.expense_type_seasonal
    )

    var selectedIndex by remember { mutableIntStateOf(0) }

    SingleChoiceSegmentedButtonRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        options.forEachIndexed { index, (type, stringRes) ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = options.size
                ),
                onClick = {
                    selectedIndex = index
                    onExpenseTypeChange(type)
                },
                colors = SegmentedButtonDefaults.colors(
                    activeContentColor = MaterialTheme.colorScheme.onPrimary,
                    activeContainerColor = MaterialTheme.colorScheme.primary
                ),
                selected = index == selectedIndex,
                label = {
                    Text(
                        text = stringResource(stringRes),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            )
        }
    }
}
