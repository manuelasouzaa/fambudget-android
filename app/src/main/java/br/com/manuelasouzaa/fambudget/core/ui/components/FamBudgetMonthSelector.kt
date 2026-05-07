package br.com.manuelasouzaa.fambudget.core.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.manuelasouzaa.fambudget.feature.transactions.ui.components.MonthYearPickerDialog
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun FamBudgetMonthSelector(
    modifier: Modifier = Modifier,
    currentMonth: YearMonth,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit,
    onMonthSelected: ((YearMonth) -> Unit)? = null
) {
    val monthName = currentMonth.format(
        DateTimeFormatter.ofPattern("MMMM yyyy", Locale("pt", "BR"))
    ).replaceFirstChar { it.uppercase() }

    var showPicker by remember { mutableStateOf(false) }

    if (showPicker && onMonthSelected != null) {
        MonthYearPickerDialog(
            current = currentMonth,
            onConfirm = { showPicker = false; onMonthSelected(it) },
            onDismiss = { showPicker = false }
        )
    }

    Row(
        modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onPreviousMonth) {
            Icon(Icons.AutoMirrored.Filled.ArrowBackIos, contentDescription = null)
        }
        Text(
            text = monthName,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = if (onMonthSelected != null) Modifier.clickable { showPicker = true } else Modifier
        )
        IconButton(onClick = onNextMonth) {
            Icon(Icons.AutoMirrored.Filled.ArrowForwardIos, contentDescription = null)
        }
    }
}
