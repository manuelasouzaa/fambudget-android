package br.com.manuelasouzaa.fambudget.core.util

object InputValidator {

    fun isEmailValid(email: String): Boolean =
        email.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))

    fun isPhoneNumberValid(phoneNumber: String): Boolean =
        phoneNumber.trim().filter { it.isDigit() }.length == 11

    fun isPasswordValid(password: String): Boolean =
        password.length >= 8 &&
                password.any { it.isUpperCase() } &&
                password.any { !it.isLetterOrDigit() }

    fun isEmpty(value: String): Boolean = value.isEmpty()

}
