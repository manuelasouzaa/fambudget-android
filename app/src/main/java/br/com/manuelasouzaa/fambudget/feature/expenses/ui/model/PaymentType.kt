package br.com.manuelasouzaa.fambudget.feature.expenses.ui.model

enum class PaymentType(val id: Int, val label: String) {
    CASH(1, "Dinheiro"),
    CREDIT(2, "Crédito"),
    DEBIT(3, "Débito"),
    PIX(4, "Pix"),
    BOLETO(5, "Boleto")
}
