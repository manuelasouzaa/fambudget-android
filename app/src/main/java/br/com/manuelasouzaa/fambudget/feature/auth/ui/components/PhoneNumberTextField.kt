package br.com.manuelasouzaa.fambudget.feature.auth.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import br.com.manuelasouzaa.fambudget.R

@Composable
fun PhoneNumberTextField(
    phoneNumber: String,
    onPhoneNumberChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = phoneNumber,
        onValueChange = { newValue ->
            val filtered = newValue.filter { it.isDigit() }.take(11)
            onPhoneNumberChange(filtered)
        },
        singleLine = true,
        textStyle = MaterialTheme.typography.bodyLarge,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Phone
        ),
        shape = RoundedCornerShape(10.dp),
        visualTransformation = PhoneVisualTransformation(),
        label = { Text(stringResource(R.string.auth_fields_phone_number_hint)) },
    )
}

class PhoneVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val digits = text.text

        val formatted = buildString {
            digits.forEachIndexed { i, c ->
                when (i) {
                    0 -> append("($c")
                    1 -> append("$c) ")
                    7 -> append("-$c")
                    else -> append(c)
                }
            }
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return when {
                    offset <= 0 -> 0
                    offset <= 2 -> offset + 1
                    offset <= 7 -> offset + 3
                    offset <= 11 -> offset + 4
                    else -> formatted.length
                }
            }

            override fun transformedToOriginal(offset: Int): Int {
                return when {
                    offset <= 0 -> 0
                    offset <= 2 -> offset - 1
                    offset <= 4 -> 2
                    offset <= 10 -> offset - 3
                    offset <= 15 -> offset - 4
                    else -> digits.length
                }
            }
        }

        return TransformedText(AnnotatedString(formatted), offsetMapping)
    }
}
