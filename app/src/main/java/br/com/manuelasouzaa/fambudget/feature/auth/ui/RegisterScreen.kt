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
import br.com.manuelasouzaa.fambudget.feature.auth.ui.components.PasswordTextField
import br.com.manuelasouzaa.fambudget.feature.auth.ui.components.PhoneNumberTextField
import br.com.manuelasouzaa.fambudget.feature.auth.ui.viewmodel.RegisterUiState
import br.com.manuelasouzaa.fambudget.feature.auth.ui.viewmodel.RegisterViewModel

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel,
    snackbarHostState: SnackbarHostState,
    onSnackbarTypeChange: (SnackbarType) -> Unit,
    onBackClick: () -> Unit,
    onRegistered: (email: String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarMessage = uiState.snackbarMessage
    val message = snackbarMessage?.text?.asString()
    val successMessage = stringResource(R.string.register_success_message)

    LaunchedEffect(Unit) {
        viewModel.registeredEmail.collect { email ->
            onRegistered(email)
            onSnackbarTypeChange(SnackbarType.INFO)
            snackbarHostState.currentSnackbarData?.dismiss()
            snackbarHostState.showSnackbar(successMessage)
        }
    }

    LaunchedEffect(snackbarMessage) {
        message?.let {
            onSnackbarTypeChange(snackbarMessage.type)
            snackbarHostState.currentSnackbarData?.dismiss()
            snackbarHostState.showSnackbar(it)
            viewModel.clearSnackbar()
        }
    }

    RegisterContent(
        modifier = modifier,
        uiState = uiState,
        onNameChange = viewModel::onFullNameChange,
        onEmailChange = viewModel::onEmailChange,
        onPhoneNumberChange = viewModel::onPhoneNumberChange,
        onPasswordChange = viewModel::onPasswordChange,
        onConfirmPasswordChange = viewModel::onConfirmPasswordChange,
        onRegisterClick = viewModel::register,
        onBackClick = onBackClick
    )
}

@Composable
private fun RegisterContent(
    modifier: Modifier = Modifier,
    uiState: RegisterUiState,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onRegisterClick: () -> Unit,
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
            modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_fambudget),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth(.2f)
                        .wrapContentHeight()
                        .padding(bottom = 20.dp)
                )

                Text(
                    text = stringResource(R.string.register_title),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = stringResource(R.string.register_subtitle),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = uiState.fullName,
                        onValueChange = onNameChange,
                        singleLine = true,
                        textStyle = MaterialTheme.typography.bodyLarge,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Text
                        ),
                        shape = RoundedCornerShape(10.dp),
                        label = { Text(stringResource(R.string.auth_fields_name_hint)) },
                    )

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = uiState.email,
                        onValueChange = onEmailChange,
                        singleLine = true,
                        textStyle = MaterialTheme.typography.bodyLarge,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Email
                        ),
                        shape = RoundedCornerShape(10.dp),
                        label = { Text(stringResource(R.string.auth_fields_email_hint)) },
                    )

                    PhoneNumberTextField(
                        phoneNumber = uiState.phoneNumber,
                        onPhoneNumberChange = onPhoneNumberChange
                    )

                    PasswordTextField(
                        label = R.string.auth_fields_password_hint,
                        password = uiState.password,
                        onPasswordChange = onPasswordChange,
                        imeAction = ImeAction.Next
                    )

                    PasswordTextField(
                        label = R.string.auth_fields_confirm_password_hint,
                        password = uiState.confirmPassword,
                        onPasswordChange = onConfirmPasswordChange
                    )
                }

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    onClick = onRegisterClick,
                    shape = RoundedCornerShape(5.dp),
                    enabled = !uiState.isLoading,
                ) {
                    if (uiState.isLoading)
                        CircularProgressIndicator()
                    else
                        Text(
                            text = stringResource(R.string.register_enter_button_label),
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(4.dp)
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
private fun RegisterScreenPreview() {
    FamBudgetTheme {
        Surface {
            RegisterContent(
                uiState = RegisterUiState(),
                modifier = Modifier.fillMaxSize(),
                onNameChange = { _ -> },
                onEmailChange = { _ -> },
                onPhoneNumberChange = { _ -> },
                onPasswordChange = { _ -> },
                onConfirmPasswordChange = { _ -> },
                onRegisterClick = {}
            ) { }
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun RegisterScreenDarkModePreview() {
    FamBudgetTheme {
        Surface {
            RegisterContent(
                uiState = RegisterUiState(),
                modifier = Modifier.fillMaxSize(),
                onNameChange = { _ -> },
                onEmailChange = { _ -> },
                onPhoneNumberChange = { _ -> },
                onPasswordChange = { _ -> },
                onConfirmPasswordChange = { _ -> },
                onRegisterClick = {}
            ) { }
        }
    }
}
