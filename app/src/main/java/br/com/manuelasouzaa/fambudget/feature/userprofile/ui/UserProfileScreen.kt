package br.com.manuelasouzaa.fambudget.feature.userprofile.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import br.com.manuelasouzaa.fambudget.R

@Composable
fun UserProfileScreen(modifier: Modifier = Modifier, onLogoutClick: () -> Unit) {
    Column(
        modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = onLogoutClick) {
            Text(stringResource(R.string.logout))
        }
    }
}
