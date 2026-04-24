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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.manuelasouzaa.fambudget.R
import br.com.manuelasouzaa.fambudget.core.ui.model.SnackbarType
import br.com.manuelasouzaa.fambudget.core.ui.theme.FamBudgetTheme
import br.com.manuelasouzaa.fambudget.ext.asString
import br.com.manuelasouzaa.fambudget.feature.auth.ui.components.CodeTextField
import br.com.manuelasouzaa.fambudget.feature.auth.ui.components.PasswordTextField
import br.com.manuelasouzaa.fambudget.feature.auth.ui.viewmodel.ResetPasswordUiState
import br.com.manuelasouzaa.fambudget.feature.auth.ui.viewmodel.ResetPasswordViewModel

@Composable
fun ResetPasswordScreen(
    modifier: Modifier = Modifier,
    viewModel: ResetPasswordViewModel,
    snackbarHostState: SnackbarHostState,
    onSnackbarTypeChange: (SnackbarType) -> Unit,
    onBackClick: () -> Unit,
    onLoginClick: () -> Unit,
    email: String
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
        if (uiState.isSuccess) onLoginClick()
    }

    ResetPasswordScreen(
        modifier = modifier.fillMaxSize(),
        uiState = uiState,
        onConfirmCodeClick = viewModel::confirm,
        onPasswordChange = viewModel::onPasswordChange,
        onConfirmResetPassword = { viewModel.confirmResetPassword(email) },
        onConfirmPasswordChange = viewModel::onConfirmPasswordChange,
        onBackClick = onBackClick,
        onCodeChange = viewModel::onCodeChange
    )
}

@Composable
private fun ResetPasswordScreen(
    modifier: Modifier = Modifier,
    uiState: ResetPasswordUiState,
    onConfirmCodeClick: () -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onConfirmResetPassword: () -> Unit,
    onBackClick: () -> Unit,
    onCodeChange: (String) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
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
                    .fillMaxSize(.9f)
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

                if (!uiState.isValid) {
                    Text(
                        text = stringResource(R.string.validate_code),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Text(
                        text = stringResource(R.string.validate_code_subtitle),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondary
                    )

                    CodeTextField(
                        code = uiState.code,
                        onCodeChange = onCodeChange
                    )

                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = onConfirmCodeClick,
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
                } else {
                    Text(
                        text = stringResource(R.string.reset_password_title),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Text(
                        text = stringResource(R.string.reset_password_subtitle),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondary
                    )

                    PasswordTextField(
                        label = R.string.reset_new_password,
                        password = uiState.password,
                        onPasswordChange = onPasswordChange,
                        imeAction = ImeAction.Next
                    )

                    PasswordTextField(
                        label = R.string.auth_fields_confirm_password_hint,
                        password = uiState.confirmPassword,
                        onPasswordChange = onConfirmPasswordChange,
                    )

                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp),
                        onClick = { onConfirmResetPassword() },
                        shape = RoundedCornerShape(5.dp),
                        enabled = !uiState.isLoading,
                    ) {
                        if (uiState.isLoading)
                            CircularProgressIndicator()
                        else
                            Text(
                                text = stringResource(R.string.forgot_password_button_label),
                                color = MaterialTheme.colorScheme.onPrimary,
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(4.dp)
                            )
                    }
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
private fun ResetPasswordScreenS() {
    FamBudgetTheme {
        Surface {
            ResetPasswordScreen(
                modifier = Modifier.fillMaxSize(),
                uiState = ResetPasswordUiState(),
                onConfirmCodeClick = {},
                onBackClick = {},
                onConfirmResetPassword = {},
                onPasswordChange = {},
                onConfirmPasswordChange = {},
                onCodeChange = {}
            )
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun ResetPasswordScreenDarkMode() {
    FamBudgetTheme {
        Surface {
            ResetPasswordScreen(
                modifier = Modifier.fillMaxSize(),
                uiState = ResetPasswordUiState(),
                onConfirmCodeClick = {},
                onConfirmResetPassword = {},
                onPasswordChange = {},
                onConfirmPasswordChange = {},
                onBackClick = {},
                onCodeChange = {}
            )
        }
    }
}
