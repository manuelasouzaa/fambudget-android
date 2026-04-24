package br.com.manuelasouzaa.fambudget.feature.auth.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.manuelasouzaa.fambudget.R
import br.com.manuelasouzaa.fambudget.core.ui.model.SnackbarType
import br.com.manuelasouzaa.fambudget.core.ui.theme.FamBudgetTheme
import br.com.manuelasouzaa.fambudget.ext.asString
import br.com.manuelasouzaa.fambudget.feature.auth.ui.viewmodel.ForgotPasswordUiState
import br.com.manuelasouzaa.fambudget.feature.auth.ui.viewmodel.ForgotPasswordViewModel

@Composable
fun ForgotPasswordScreen(
    modifier: Modifier = Modifier,
    viewModel: ForgotPasswordViewModel,
    snackbarHostState: SnackbarHostState,
    onSnackbarTypeChange: (SnackbarType) -> Unit,
    onResetPasswordClick: (String) -> Unit,
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarMessage = uiState.snackbarMessage
    val message = snackbarMessage?.text?.asString()

    LaunchedEffect(snackbarMessage) {
        message?.let {
            onSnackbarTypeChange(snackbarMessage.type)
            snackbarHostState.currentSnackbarData?.dismiss()
            snackbarHostState.showSnackbar(it)
            viewModel.clearSnackbar()
        }
    }

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            viewModel.clearSuccess()
            onResetPasswordClick(uiState.email)
        }
    }

    ForgotPasswordContent(
        modifier = modifier.fillMaxSize(),
        uiState = uiState,
        onEmailChange = viewModel::onEmailChange,
        onConfirmClick = viewModel::confirm,
        onBackClick = onBackClick
    )
}

@Composable
private fun ForgotPasswordContent(
    modifier: Modifier = Modifier,
    uiState: ForgotPasswordUiState,
    onEmailChange: (String) -> Unit,
    onConfirmClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        IconButton(onClick = { onBackClick() }) {
            Icon(imageVector = Icons.Filled.ArrowBackIosNew, contentDescription = null)
        }
        Box(
            modifier = modifier.background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(.8f)
                    .background(MaterialTheme.colorScheme.background),
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_fambudget),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth(.25f)
                        .wrapContentHeight()
                )

                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = stringResource(R.string.forgot_password_title),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Text(
                    text = stringResource(R.string.forgot_password_subtitle),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondary
                )

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.email,
                    onValueChange = onEmailChange,
                    textStyle = MaterialTheme.typography.bodyLarge,
                    singleLine = true,
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Email
                    ),
                    shape = RoundedCornerShape(10.dp),
                    label = { Text(stringResource(R.string.auth_fields_email_hint)) },
                )

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onConfirmClick() },
                    shape = RoundedCornerShape(5.dp),
                    enabled = !uiState.isLoading
                ) {
                    if (uiState.isLoading)
                        CircularProgressIndicator()
                    else
                        Text(
                            text = stringResource(R.string.forgot_password_button_label),
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.titleMedium
                        )
                }

                Text(
                    text = stringResource(R.string.return_title),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .clickable { onBackClick() },
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    FamBudgetTheme {
        Surface {
            ForgotPasswordContent(
                modifier = Modifier.fillMaxSize(),
                uiState = ForgotPasswordUiState(),
                onEmailChange = {},
                onConfirmClick = {},
                onBackClick = {}
            )
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun LoginScreenDarkModePreview() {
    FamBudgetTheme {
        Surface {
            ForgotPasswordContent(
                modifier = Modifier.fillMaxSize(),
                uiState = ForgotPasswordUiState(),
                onEmailChange = {},
                onConfirmClick = {},
                onBackClick = {}
            )
        }
    }
}
