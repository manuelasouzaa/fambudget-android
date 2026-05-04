package br.com.manuelasouzaa.fambudget.feature.transactions.ui.components

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.manuelasouzaa.fambudget.R
import kotlinx.coroutines.flow.distinctUntilChanged
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

private val ITEM_HEIGHT = 48.dp
private const val VISIBLE_ITEMS = 3

@Composable
fun MonthYearPickerDialog(
    current: YearMonth,
    onConfirm: (YearMonth) -> Unit,
    onDismiss: () -> Unit
) {
    val months = (1..12).toList()
    val currentYear = current.year
    val years = ((currentYear - 10)..(currentYear + 10)).toList()
    val monthFormatter = remember { DateTimeFormatter.ofPattern("MMMM", Locale("pt", "BR")) }
    val monthNames = remember {
        months.map { monthFormatter.format(java.time.Month.of(it)).replaceFirstChar { c -> c.uppercase() } }
    }

    var selectedMonth by remember { mutableIntStateOf(current.monthValue) }
    var selectedYear by remember { mutableIntStateOf(currentYear) }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = { onConfirm(YearMonth.of(selectedYear, selectedMonth)) }) {
                Text(text = stringResource(R.string.save_title))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.return_title))
            }
        },
        title = {
            Text(
                text = stringResource(R.string.select_period),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        text = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                WheelPicker(
                    items = monthNames,
                    selectedIndex = months.indexOf(selectedMonth),
                    onItemSelected = { selectedMonth = months[it] },
                    modifier = Modifier.weight(1f)
                )
                WheelPicker(
                    items = years.map { it.toString() },
                    selectedIndex = years.indexOf(selectedYear),
                    onItemSelected = { selectedYear = years[it] },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    )
}

@Composable
private fun WheelPicker(
    items: List<String>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = selectedIndex)

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .distinctUntilChanged()
            .collect { index ->
                if (index in items.indices) onItemSelected(index)
            }
    }

    Box(modifier = modifier.height(ITEM_HEIGHT * VISIBLE_ITEMS)) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxWidth(),
            flingBehavior = rememberSnapFlingBehavior(listState)
        ) {
            item { Box(Modifier.height(ITEM_HEIGHT)) }
            items.forEachIndexed { index, item ->
                item {
                    val isSelected by remember { derivedStateOf { listState.firstVisibleItemIndex == index } }
                    Text(
                        text = item,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(ITEM_HEIGHT)
                            .padding(vertical = 12.dp),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f)
                    )
                }
            }
            item { Box(Modifier.height(ITEM_HEIGHT)) }
        }

        Column(
            modifier = Modifier.matchParentSize(),
            verticalArrangement = Arrangement.Center
        ) {
            HorizontalDivider(color = MaterialTheme.colorScheme.primary)
            Box(Modifier.height(ITEM_HEIGHT))
            HorizontalDivider(color = MaterialTheme.colorScheme.primary)
        }
    }
}
