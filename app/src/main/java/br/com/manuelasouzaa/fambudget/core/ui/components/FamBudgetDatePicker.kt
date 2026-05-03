package br.com.manuelasouzaa.fambudget.core.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import br.com.manuelasouzaa.fambudget.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FamBudgetDatePicker(
    modifier: Modifier = Modifier,
    value: String,
    maxDateMillis: Long? = null,
    onDateSelected: (String) -> Unit
) {
    var showPicker by remember { mutableStateOf(false) }
    val selectableDates = remember(maxDateMillis) {
        object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long) =
                maxDateMillis == null || utcTimeMillis <= maxDateMillis
        }
    }
    val datePickerState = rememberDatePickerState(selectableDates = selectableDates)

    if (showPicker) {
        DatePickerDialog(
            onDismissRequest = { showPicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let {
                        onDateSelected(convertMillisToDate(it))
                    }
                    showPicker = false
                }) { Text("OK") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Box(modifier) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value.toDisplayDate(),
            onValueChange = {},
            readOnly = true,
            shape = RoundedCornerShape(10.dp),
            trailingIcon = {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(R.drawable.ic_calendar),
                    tint = MaterialTheme.colorScheme.onSecondary,
                    contentDescription = null
                )
            }
        )
        Box(Modifier
            .matchParentSize()
            .clickable { showPicker = true })
    }
}

fun convertMillisToDate(millis: Long): String {
    val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
    calendar.timeInMillis = millis
    return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }.format(calendar.time)
}

// Converts stored ISO "yyyy-MM-dd" to display format "dd/MM/yyyy"
private fun String.toDisplayDate(): String {
    if (isBlank()) return this
    return try {
        val parsed = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(this)
        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(parsed!!)
    } catch (e: Exception) {
        this
    }
}
