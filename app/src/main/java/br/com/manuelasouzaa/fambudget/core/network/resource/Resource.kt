package br.com.manuelasouzaa.fambudget.core.network.resource

import androidx.annotation.StringRes

sealed class Resource<out T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error(
        val statusCode: Int? = null,
        val message: String? = null,
        val uiMessage: UiText? = null
    ) : Resource<Nothing>()
}

sealed class UiText {
    data class Resource(@StringRes val resId: Int) : UiText()
    data class Dynamic(val value: String) : UiText()
}
