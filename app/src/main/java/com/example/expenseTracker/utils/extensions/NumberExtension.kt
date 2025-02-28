package com.example.expenseTracker.utils.extensions

import java.text.NumberFormat
import java.util.Locale

fun Number.toCurrency(locale: Locale = Locale("vi", "VN")): String {
    val currencyFormat = NumberFormat.getCurrencyInstance(locale)
    return currencyFormat.format(this)
}