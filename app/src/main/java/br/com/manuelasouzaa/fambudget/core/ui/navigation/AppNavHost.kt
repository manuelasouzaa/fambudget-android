package br.com.manuelasouzaa.fambudget.core.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.manuelasouzaa.fambudget.core.ui.components.FamBudgetNavBar
import br.com.manuelasouzaa.fambudget.core.ui.components.FamBudgetSnackbar
import br.com.manuelasouzaa.fambudget.core.ui.components.FamBudgetTopBar
import br.com.manuelasouzaa.fambudget.core.ui.model.SnackbarType
import br.com.manuelasouzaa.fambudget.feature.budgets.ui.BudgetsScreen
import br.com.manuelasouzaa.fambudget.feature.category.ui.CategoriesScreen
import br.com.manuelasouzaa.fambudget.feature.expenses.ui.CreateExpenseFormScreen
import br.com.manuelasouzaa.fambudget.feature.expenses.ui.ExpensesScreen
import br.com.manuelasouzaa.fambudget.feature.expenses.ui.PaidExpensesScreen
import br.com.manuelasouzaa.fambudget.feature.expenses.ui.PendingExpensesScreen
import br.com.manuelasouzaa.fambudget.feature.family.ui.FamilyScreen
import br.com.manuelasouzaa.fambudget.feature.home.ui.HomeScreen
import br.com.manuelasouzaa.fambudget.feature.income.ui.AddIncomeFormScreen
import br.com.manuelasouzaa.fambudget.feature.income.ui.IncomeScreen
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


    fun onSnackbarTypeChange(type: SnackbarType) {
        snackbarType = type
    }

    val fullScreenRoutes = listOf(
        ScreenDestinations.AddExpenseFormScreen.name,
        ScreenDestinations.AddIncomeFormScreen.ROUTE
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (route !in fullScreenRoutes)
                FamBudgetTopBar(
                    isMenuSelected = route == ScreenDestinations.MenuScreen.name
                ) {
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
            if (route !in fullScreenRoutes)
                FamBudgetNavBar(modifier, route) {
                    navController.navigate(it) {
                        popUpTo(ScreenDestinations.HomeScreen.name) { inclusive = false }
                        launchSingleTop = true
                    }
                }
        }
    ) { innerPadding ->

        NavHost(
            modifier = modifier.padding(innerPadding),
            navController = navController,
            startDestination = ScreenDestinations.HomeScreen.name,
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() },
            popExitTransition = { fadeOut() }
        ) {
            composable(ScreenDestinations.HomeScreen.name) { backStackEntry ->
                val lifecycleState by backStackEntry.lifecycle.currentStateFlow.collectAsState()
                HomeScreen(
                    modifier = modifier,
                    refreshTrigger = lifecycleState,
                    onNavigateToNewIncome = {
                        navController.navigate(ScreenDestinations.AddIncomeFormScreen.route())
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

            composable(
                route = ScreenDestinations.MenuScreen.name,
                enterTransition = { slideInHorizontally(tween(150)) { it } },
                exitTransition = { slideOutHorizontally(tween(150)) { it } },
                popEnterTransition = { slideInHorizontally(tween(150)) { it } },
                popExitTransition = { slideOutHorizontally(tween(150)) { it } }
            ) {
                MenuScreen(
                    modifier = modifier,
                ) { destination ->
                    navController.navigate(destination.name)
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
                    modifier,
                    snackbarHostState = snackbarHostState,
                    onSnackbarTypeChange = ::onSnackbarTypeChange,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable(ScreenDestinations.ExpensesScreen.name) {
                ExpensesScreen(modifier)
            }

            composable(ScreenDestinations.PendingExpensesScreen.name) {
                PendingExpensesScreen(modifier)
            }

            composable(ScreenDestinations.PaidExpensesScreen.name) {
                PaidExpensesScreen(modifier)
            }

            composable(ScreenDestinations.IncomeScreen.name) { backStackEntry ->
                val lifecycleState by backStackEntry.lifecycle.currentStateFlow.collectAsState()
                IncomeScreen(
                    modifier = modifier,
                    refreshTrigger = lifecycleState,
                    onNavigateToNewIncome = {
                        navController.navigate(ScreenDestinations.AddIncomeFormScreen.route())
                    },
                    onNavigateToEditIncome = { income ->
                        val valueCents = (income.value * 100).toLong().toString()
                        navController.navigate(
                            ScreenDestinations.AddIncomeFormScreen.route(
                                incomeId = income.id,
                                value = valueCents,
                                date = income.dateInitial ?: "",
                                description = income.description ?: ""
                            )
                        )
                    }
                )
            }

            composable(ScreenDestinations.CategoriesScreen.name) {
                CategoriesScreen(modifier)
            }

            composable(ScreenDestinations.FamilyScreen.name) {
                FamilyScreen(modifier)
            }
        }
    }
}
