package br.com.manuelasouzaa.fambudget.core.ui.model

sealed class FormEvent(open val message: Int) {
    data class Error(override val message: Int, val navigateBack: Boolean = false) :
        FormEvent(message)

    data class Success(override val message: Int) : FormEvent(message)
}
