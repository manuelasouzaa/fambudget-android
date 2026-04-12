package br.com.manuelasouzaa.fambudget.feature.auth.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import br.com.manuelasouzaa.fambudget.R

@Composable
fun PasswordTextField(
    @StringRes label: Int,
    password: String,
    onPasswordChange: (String) -> Unit,
    imeAction: ImeAction = ImeAction.Done
) {
    var passwordVisible by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    val visualTransformation =
        if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()

    val icon = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff

    val iconDescription = if (passwordVisible) {
        R.string.content_description_hide_password
    } else {
        R.string.content_description_show_password
    }

    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        modifier = Modifier.fillMaxWidth(),
        textStyle = MaterialTheme.typography.bodyLarge,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = imeAction,
            keyboardType = KeyboardType.Password
        ),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) },
            onDone = { focusManager.clearFocus() }
        ),
        shape = RoundedCornerShape(10.dp),
        label = { Text(stringResource(label)) },
        singleLine = true,
        visualTransformation = visualTransformation,
        trailingIcon = {
            IconButton(
                onClick = { passwordVisible = !passwordVisible },
                modifier = Modifier.focusProperties { canFocus = false }
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = stringResource(iconDescription)
                )
            }
        }
    )
}
