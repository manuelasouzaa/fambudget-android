package br.com.manuelasouzaa.fambudget.core.ui.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.manuelasouzaa.fambudget.core.ui.model.SnackbarType
import br.com.manuelasouzaa.fambudget.feature.auth.ui.LoginScreen
import br.com.manuelasouzaa.fambudget.feature.auth.ui.RegisterScreen
import br.com.manuelasouzaa.fambudget.feature.auth.ui.viewmodel.LoginViewModel
import br.com.manuelasouzaa.fambudget.feature.auth.ui.viewmodel.RegisterViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AuthNavHost(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    onSnackbarTypeChange: (SnackbarType) -> Unit
) {
    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
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
                onSnackbarTypeChange = onSnackbarTypeChange,
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
                onSnackbarTypeChange = onSnackbarTypeChange,
                onBackClick = { navController.popBackStack() },
                onRegistered = { email ->
                    navController.previousBackStackEntry?.savedStateHandle?.set("email", email)
                    navController.popBackStack()
                }
            )
        }
    }
}
