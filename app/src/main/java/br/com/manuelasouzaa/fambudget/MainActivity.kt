package br.com.manuelasouzaa.fambudget

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.manuelasouzaa.fambudget.core.ui.model.SnackbarType
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
            val snackbarHostState = remember { SnackbarHostState() }

            FamBudgetTheme {
                var snackbarType by remember { mutableStateOf(SnackbarType.ERROR) }

                val containerColor = when (snackbarType) {
                    SnackbarType.ERROR -> MaterialTheme.colorScheme.errorContainer
                    SnackbarType.INFO -> MaterialTheme.colorScheme.primaryContainer
                }

                val contentColor = when (snackbarType) {
                    SnackbarType.ERROR -> MaterialTheme.colorScheme.onErrorContainer
                    SnackbarType.INFO -> MaterialTheme.colorScheme.onPrimaryContainer
                }

                Scaffold(
                    Modifier.fillMaxSize(),
                    snackbarHost = {
                        SnackbarHost(
                            hostState = snackbarHostState,
                            modifier = Modifier
                                .navigationBarsPadding()
                                .padding(16.dp)
                        ) { data ->
                            Snackbar(
                                modifier = Modifier.fillMaxWidth(),
                                containerColor = containerColor,
                                contentColor = contentColor,
                                shape = RoundedCornerShape(5.dp),
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight()
                                        .padding(4.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = data.visuals.message,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.wrapContentSize()
                                    )
                                }
                            }
                        }
                    }
                ) { innerPadding ->

                    val modifier = Modifier.padding(innerPadding)

                    when (uiState) {
                        MainScreenUiState.Auth -> AuthNavHost(modifier, snackbarHostState) {
                            snackbarType = it
                        }

                        MainScreenUiState.Loaded -> AppNavHost(
                            modifier,
                            onLogoutClick = rootViewModel::logout
                        )

                        MainScreenUiState.Loading -> {}
                    }
                }
            }
        }
    }
}
