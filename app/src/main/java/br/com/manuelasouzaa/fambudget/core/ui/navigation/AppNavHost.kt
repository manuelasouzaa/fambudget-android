package br.com.manuelasouzaa.fambudget.core.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.Lifecycle
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.com.manuelasouzaa.fambudget.core.ui.components.FamBudgetNavBar
import br.com.manuelasouzaa.fambudget.core.ui.components.FamBudgetSnackbar
import br.com.manuelasouzaa.fambudget.core.ui.components.FamBudgetTopBar
import br.com.manuelasouzaa.fambudget.core.ui.model.SnackbarType
import br.com.manuelasouzaa.fambudget.feature.budgets.ui.BudgetsScreen
import br.com.manuelasouzaa.fambudget.feature.expenses.ui.CreateExpenseFormScreen
import br.com.manuelasouzaa.fambudget.feature.home.ui.HomeScreen
import br.com.manuelasouzaa.fambudget.feature.income.ui.AddIncomeFormScreen
import br.com.manuelasouzaa.fambudget.feature.menu.ui.MenuScreen
import br.com.manuelasouzaa.fambudget.feature.reports.ui.ReportsScreen
import br.com.manuelasouzaa.fambudget.feature.transactions.ui.TransactionsScreen
import br.com.manuelasouzaa.fambudget.feature.userprofile.ui.UserProfileScreen

@Composable
fun AppNavHost(modifier: Modifier = Modifier, onLogoutClick: () -> Unit) {
    val snackbarHostState = remember { SnackbarHostState() }
    var snackbarType by remember { mutableStateOf(SnackbarType.ERROR) }

    val navController = rememberNavController()
    val currentRoute by navController.currentBackStackEntryAsState()
    val route = currentRoute?.destination?.route

    val routesWithHiddenBottomBar = listOf(
        ScreenDestinations.MenuScreen.name,
        ScreenDestinations.AddExpenseFormScreen.name,
        ScreenDestinations.AddIncomeFormScreen.name
    )

    fun onSnackbarTypeChange(type: SnackbarType) {
        snackbarType = type
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (route !in routesWithHiddenBottomBar)
                FamBudgetTopBar {
                    navController.navigate(ScreenDestinations.MenuScreen.name)
                }
        },
        snackbarHost = {
            FamBudgetSnackbar(
                snackbarHostState = snackbarHostState,
                snackbarType = snackbarType
            )
        },
        bottomBar = {
            if (route !in routesWithHiddenBottomBar)
                FamBudgetNavBar(modifier, route) {
                    navController.navigate(it)
                }
        }
    ) { innerPadding ->

        NavHost(
            modifier = modifier.padding(innerPadding),
            navController = navController,
            startDestination = ScreenDestinations.HomeScreen.name
        ) {
            composable(ScreenDestinations.HomeScreen.name) { backStackEntry ->
                val lifecycleState by backStackEntry.lifecycle.currentStateFlow.collectAsState()
                HomeScreen(
                    modifier = modifier,
                    refreshTrigger = lifecycleState,
                    onNavigateToNewIncome = {
                        navController.navigate(
                            ScreenDestinations.AddIncomeFormScreen.name
                        )
                    },
                    onNavigateToNewExpense = {
                        navController.navigate(ScreenDestinations.AddExpenseFormScreen.name)
                    }
                )
            }

            composable(ScreenDestinations.TransactionsScreen.name) {
                TransactionsScreen(modifier)
            }

            composable(ScreenDestinations.BudgetsScreen.name) {
                BudgetsScreen(modifier)
            }

            composable(ScreenDestinations.MenuScreen.name) {
                MenuScreen(modifier) {
                    navController.popBackStack()
                }
            }

            composable(ScreenDestinations.ReportsScreen.name) {
                ReportsScreen(modifier)
            }

            composable(ScreenDestinations.UserProfileScreen.name) {
                UserProfileScreen(modifier, onLogoutClick = onLogoutClick)
            }

            composable(ScreenDestinations.AddExpenseFormScreen.name) {
                CreateExpenseFormScreen(
                    modifier,
                    snackbarHostState = snackbarHostState,
                    onSnackbarTypeChange = ::onSnackbarTypeChange,
                    onNavigateBack = { navController.popBackStack() })
            }

            composable(ScreenDestinations.AddIncomeFormScreen.name) {
                AddIncomeFormScreen()
            }
        }
    }
}
