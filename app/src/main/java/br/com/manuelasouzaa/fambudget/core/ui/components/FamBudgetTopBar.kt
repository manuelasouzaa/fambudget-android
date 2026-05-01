package br.com.manuelasouzaa.fambudget.core.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.com.manuelasouzaa.fambudget.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FamBudgetTopBar(
    onMenuIconClick: () -> Unit
) {
    Column {
        TopAppBar(
            title = {
            Text(
                text = stringResource(R.string.app_name),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        },
            modifier = Modifier.fillMaxWidth(),
            navigationIcon = {
                Image(
                    painter = painterResource(R.drawable.ic_fambudget),
                    contentDescription = null,
                    modifier = Modifier
                        .width(50.dp)
                        .wrapContentHeight()
                        .padding(8.dp)
                )
            }, actions = {
                IconButton(
                    onClick = onMenuIconClick,
                    modifier = Modifier.size(50.dp).padding(8.dp),
                    colors = IconButtonDefaults.iconButtonColors()
                        .copy(contentColor = MaterialTheme.colorScheme.tertiary)
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_menu),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.tertiary)
                    )
                }
            })

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            1.dp,
            color = MaterialTheme.colorScheme.tertiary
        )
    }
}
