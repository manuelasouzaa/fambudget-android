package br.com.manuelasouzaa.fambudget.ext

import java.text.NumberFormat
import java.util.Locale

fun Double.toCurrency(): String {
    val locale = Locale.Builder().setLanguage("pt").setRegion("BR").build()
    return NumberFormat.getCurrencyInstance(locale).format(this)
}
