package br.com.manuelasouzaa.fambudget.ext

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import br.com.manuelasouzaa.fambudget.R
import br.com.manuelasouzaa.fambudget.core.network.resource.UiText

@Composable
fun UiText.asString(): String = when (this) {
    is UiText.Dynamic -> value
    is UiText.Resource -> stringResource(resId)
}

@StringRes
fun UiText?.toStringRes(): Int = when (this) {
    is UiText.Resource -> resId
    else -> R.string.error_unknown
}
