package br.com.manuelasouzaa.fambudget.core.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.manuelasouzaa.fambudget.R
import br.com.manuelasouzaa.fambudget.core.ui.components.FamBudgetSnackbar
import br.com.manuelasouzaa.fambudget.core.ui.model.SnackbarType
import br.com.manuelasouzaa.fambudget.feature.auth.ui.LoginScreen
import br.com.manuelasouzaa.fambudget.feature.auth.ui.RegisterScreen
import br.com.manuelasouzaa.fambudget.feature.auth.ui.viewmodel.LoginViewModel
import br.com.manuelasouzaa.fambudget.feature.auth.ui.viewmodel.RegisterViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AuthNavHost(sessionExpired: Boolean = false) {
    val snackbarHostState = remember { SnackbarHostState() }
    var snackbarType by remember { mutableStateOf(SnackbarType.ERROR) }

    val expiredSessionMessage = stringResource(R.string.expired_session)

    LaunchedEffect(sessionExpired) {
        if (sessionExpired) {
            snackbarType = SnackbarType.ERROR
            snackbarHostState.showSnackbar(expiredSessionMessage)
        }
    }

    fun onSnackbarTypeChange(type: SnackbarType) {
        snackbarType = type
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            FamBudgetSnackbar(
                snackbarHostState = snackbarHostState,
                snackbarType = snackbarType,
            )
        }
    ) { innerPadding ->

        val navController = rememberNavController()

        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = AuthScreenDestinations.LoginScreen.name
        ) {
            composable(AuthScreenDestinations.LoginScreen.name) { backStackEntry ->
                val viewModel = koinViewModel<LoginViewModel>()
                val email = backStackEntry.savedStateHandle.get<String>("email")

                LaunchedEffect(email) {
                    email?.let {
                        viewModel.onEmailChange(it)
                        backStackEntry.savedStateHandle.remove<String>("email")
                    }
                }

                LoginScreen(
                    viewModel = viewModel,
                    snackbarHostState = snackbarHostState,
                    onSnackbarTypeChange = ::onSnackbarTypeChange,
                    onRegisterClick = {
                        navController.navigate(AuthScreenDestinations.RegisterScreen.name)
                    }
                )
            }

            composable(AuthScreenDestinations.RegisterScreen.name) {
                val viewModel = koinViewModel<RegisterViewModel>()

                RegisterScreen(
                    viewModel = viewModel,
                    snackbarHostState = snackbarHostState,
                    onSnackbarTypeChange = ::onSnackbarTypeChange,
                    onBackClick = { navController.popBackStack() },
                    onRegistered = { email ->
                        navController.previousBackStackEntry?.savedStateHandle?.set("email", email)
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
