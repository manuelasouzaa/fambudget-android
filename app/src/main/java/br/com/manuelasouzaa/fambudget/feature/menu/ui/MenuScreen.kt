package br.com.manuelasouzaa.fambudget.feature.menu.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import br.com.manuelasouzaa.fambudget.R

@Composable
fun MenuScreen(modifier: Modifier = Modifier, onBackClick: () -> Unit) {

    Box(modifier.fillMaxSize()) {
        IconButton(
            onClick = onBackClick,
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = stringResource(R.string.return_title)
            )
        }
    }

}
