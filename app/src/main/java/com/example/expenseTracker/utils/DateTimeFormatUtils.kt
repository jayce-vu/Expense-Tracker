package com.example.expenseTracker.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateTimeFormatUtils {
    fun convertAndFormatDate(date: Date): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return formatter.format(date)
    }

    fun stringToFormattedDate(dateString: String): String {
        val inputFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val outputFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

        val date: Date? = inputFormatter.parse(dateString)
        return date?.let { outputFormatter.format(it) } ?: "Invalid date"
    }
}