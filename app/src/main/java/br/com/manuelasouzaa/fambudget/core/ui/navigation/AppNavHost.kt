package br.com.manuelasouzaa.fambudget.core.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.manuelasouzaa.fambudget.core.ui.components.FamBudgetSnackbar
import br.com.manuelasouzaa.fambudget.core.ui.model.SnackbarType
import br.com.manuelasouzaa.fambudget.feature.expenses.ui.CreateExpenseFormScreen
import br.com.manuelasouzaa.fambudget.feature.income.ui.AddIncomeFormScreen
import kotlinx.coroutines.launch

private const val MAIN_ROUTE = "main"

@Composable
fun AppNavHost(modifier: Modifier = Modifier, onLogoutClick: () -> Unit) {
    val rootNavController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    var snackbarType by remember { mutableStateOf(SnackbarType.ERROR) }
    val scope = rememberCoroutineScope()

    fun showSnackbar(message: String, type: SnackbarType) {
        snackbarType = type
        scope.launch { snackbarHostState.showSnackbar(message) }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(Modifier.fillMaxSize()) {
            NavHost(
                navController = rootNavController,
                startDestination = MAIN_ROUTE,
                modifier = Modifier.fillMaxSize(),
                enterTransition = { slideInHorizontally(tween(250)) { it } },
                exitTransition = { fadeOut(tween(0)) },
                popEnterTransition = { fadeIn(tween(0)) },
                popExitTransition = { slideOutHorizontally(tween(250)) { it } }
            ) {
                composable(
                    route = MAIN_ROUTE,
                    enterTransition = { fadeIn() },
                    exitTransition = { fadeOut(tween(0)) },
                    popEnterTransition = { fadeIn(tween(0)) },
                    popExitTransition = { fadeOut() }
                ) {
                    MainScreen(
                        modifier = modifier,
                        rootNavController = rootNavController,
                        onLogoutClick = onLogoutClick
                    )
                }

                composable(ScreenDestinations.AddExpenseFormScreen.name) {
                    CreateExpenseFormScreen(
                        modifier.systemBarsPadding(),
                        onShowSnackbar = ::showSnackbar,
                        onNavigateBack = { rootNavController.popBackStack() }
                    )
                }

                composable(
                    route = ScreenDestinations.AddIncomeFormScreen.ROUTE,
                    arguments = listOf(
                        navArgument("incomeId") {
                            type = NavType.StringType; nullable = true; defaultValue = null
                        },
                        navArgument("incomeValue") { type = NavType.StringType; defaultValue = "" },
                        navArgument("incomeDate") { type = NavType.StringType; defaultValue = "" },
                        navArgument("incomeDescription") {
                            type = NavType.StringType; defaultValue = ""
                        }
                    )
                ) {
                    AddIncomeFormScreen(
                        modifier.systemBarsPadding(),
                        onShowSnackbar = ::showSnackbar,
                        onNavigateBack = { rootNavController.popBackStack() }
                    )
                }
            }

            FamBudgetSnackbar(
                snackbarHostState = snackbarHostState,
                snackbarType = snackbarType,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}
