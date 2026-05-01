package br.com.manuelasouzaa.fambudget

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import br.com.manuelasouzaa.fambudget.core.ui.navigation.AppNavHost
import br.com.manuelasouzaa.fambudget.core.ui.navigation.AuthNavHost
import br.com.manuelasouzaa.fambudget.core.ui.theme.FamBudgetTheme
import br.com.manuelasouzaa.fambudget.core.ui.viewmodel.MainScreenUiState
import br.com.manuelasouzaa.fambudget.core.ui.viewmodel.RootViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val rootViewModel: RootViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val uiState by rootViewModel.uiState.collectAsState()

            FamBudgetTheme {

                when (uiState) {
                    MainScreenUiState.Auth -> AuthNavHost()

                    MainScreenUiState.Loaded -> AppNavHost(onLogoutClick = rootViewModel::logout)

                    MainScreenUiState.Loading -> {}
                }
            }
        }
    }
}
