package br.com.manuelasouzaa.fambudget.feature.auth.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

private const val CODE_LENGTH = 5

@Composable
fun CodeTextField(
    code: String,
    onCodeChange: (String) -> Unit
) {
    val focusRequesters = remember { List(CODE_LENGTH) { FocusRequester() } }

    LaunchedEffect(Unit) {
        focusRequesters[0].requestFocus()
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .widthIn(max = 340.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
    ) {
        repeat(CODE_LENGTH) { index ->
            val char = code.getOrNull(index)
                ?.takeIf { it != ' ' }
                ?.toString() ?: ""

            OutlinedTextField(
                value = char,
                onValueChange = { input ->
                    val sanitized = input.uppercase().filter { it.isLetterOrDigit() }
                    if (sanitized.isEmpty()) {
                        val hadChar = code.getOrNull(index) != null && code.getOrNull(index) != ' '

                        val chars = code.padEnd(CODE_LENGTH, ' ').toCharArray()
                        chars[index] = ' '
                        val newCode = chars.concatToString()
                        onCodeChange(newCode)

                        if (!hadChar && index > 0) {
                            focusRequesters[index - 1].requestFocus()
                        }
                    } else {
                        val digit = sanitized.last().toString()
                        val chars = code.padEnd(CODE_LENGTH, ' ').toCharArray()
                        chars[index] = digit[0]
                        val newCode = chars.concatToString().trimEnd()
                        onCodeChange(newCode)
                        if (index < CODE_LENGTH - 1) focusRequesters[index + 1].requestFocus()
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(focusRequesters[index])
                    .onPreviewKeyEvent {
                        if (it.key == Key.Backspace && it.type == KeyEventType.KeyDown) {

                            val isEmpty = char.isEmpty()

                            if (isEmpty && index > 0) {
                                focusRequesters[index - 1].requestFocus()
                                true
                            } else {
                                false
                            }
                        } else {
                            false
                        }
                    },
                textStyle = MaterialTheme.typography.titleLarge.copy(
                    textAlign = TextAlign.Center
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = if (index == CODE_LENGTH - 1) ImeAction.Done else ImeAction.Next
                ),
                shape = RoundedCornerShape(10.dp),
            )
        }
    }
}
