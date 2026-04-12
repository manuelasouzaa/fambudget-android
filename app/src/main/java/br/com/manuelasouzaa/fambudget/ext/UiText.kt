package br.com.manuelasouzaa.fambudget.ext

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import br.com.manuelasouzaa.fambudget.core.network.resource.UiText

@Composable
fun UiText.asString(): String = when (this) {
    is UiText.Dynamic -> value
    is UiText.Resource -> stringResource(resId)
}
