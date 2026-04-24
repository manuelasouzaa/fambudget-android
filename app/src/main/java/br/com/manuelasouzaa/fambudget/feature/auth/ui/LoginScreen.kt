package br.com.manuelasouzaa.fambudget.feature.auth.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import br.com.manuelasouzaa.fambudget.feature.auth.ui.components.PasswordTextField
import br.com.manuelasouzaa.fambudget.feature.auth.ui.viewmodel.LoginUiState
import br.com.manuelasouzaa.fambudget.feature.auth.ui.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel,
    snackbarHostState: SnackbarHostState,
    onSnackbarTypeChange: (SnackbarType) -> Unit,
    onRegisterClick: () -> Unit,
    onForgotPasswordClick: () -> Unit
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

    LoginContent(
        modifier = modifier.fillMaxSize(),
        uiState = uiState,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onLoginClick = viewModel::login,
        onRegisterClick = onRegisterClick,
        onForgotPasswordClick = onForgotPasswordClick
    )
}

@Composable
private fun LoginContent(
    modifier: Modifier = Modifier,
    uiState: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onForgotPasswordClick: () -> Unit
) {
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
                text = stringResource(R.string.login_title),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = stringResource(R.string.login_subtitle),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSecondary
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.email,
                onValueChange = onEmailChange,
                textStyle = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email
                ),
                shape = RoundedCornerShape(10.dp),
                label = { Text(stringResource(R.string.auth_fields_email_hint)) },
            )

            PasswordTextField(
                label = R.string.auth_fields_password_hint,
                password = uiState.password,
                onPasswordChange = onPasswordChange
            )

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Text(
                    text = stringResource(R.string.login_forgot_password_message),
                    modifier = Modifier
                        .wrapContentSize()
                        .clickable { onForgotPasswordClick() },
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onLoginClick,
                shape = RoundedCornerShape(5.dp),
                enabled = !uiState.isLoading
            ) {
                if (uiState.isLoading)
                    CircularProgressIndicator()
                else
                    Text(
                        text = stringResource(R.string.login_enter_button_label),
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.titleMedium
                    )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = stringResource(R.string.login_do_not_have_account_message),
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = stringResource(R.string.login_register_message),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(4.dp)
                        .clickable { onRegisterClick() },
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
            LoginContent(
                modifier = Modifier.fillMaxSize(),
                uiState = LoginUiState(),
                onEmailChange = {},
                onPasswordChange = {},
                onLoginClick = {},
                onRegisterClick = {},
                onForgotPasswordClick = {}
            )
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun LoginScreenDarkModePreview() {
    FamBudgetTheme {
        Surface {
            LoginContent(
                modifier = Modifier.fillMaxSize(),
                uiState = LoginUiState(),
                onEmailChange = {},
                onPasswordChange = {},
                onLoginClick = {},
                onRegisterClick = {},
                onForgotPasswordClick = {}
            )
        }
    }
}
