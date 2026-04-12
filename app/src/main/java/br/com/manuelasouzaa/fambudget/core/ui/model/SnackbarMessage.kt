package br.com.manuelasouzaa.fambudget.core.ui.model

import br.com.manuelasouzaa.fambudget.core.network.resource.UiText

data class SnackbarMessage(
    val text: UiText,
    val type: SnackbarType = SnackbarType.ERROR
)

enum class SnackbarType {
    ERROR,
    INFO
}
