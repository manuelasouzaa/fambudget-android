package br.com.manuelasouzaa.fambudget.ext

import java.text.NumberFormat
import java.util.Locale

fun Double.toCurrency(): String {
    val locale = Locale.Builder().setLanguage("pt").setRegion("BR").build()
    return NumberFormat.getCurrencyInstance(locale).format(this)
}

// Stores digits only (e.g. "1000" = R$ 10,00). Displays as "R$ 10,00".
fun String.toCurrencyDisplay(): String {
    val digits = filter { it.isDigit() }.padStart(3, '0')
    val cents = digits.takeLast(2)
    val reais = digits.dropLast(2).trimStart('0').ifEmpty { "0" }
    return "R$ $reais,$cents"
}
