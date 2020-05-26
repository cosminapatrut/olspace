package com.orange.olsnasa.extensions

import java.text.SimpleDateFormat
import java.util.*


fun Long.toDate(outputFormat: String): String {
    val formatter = SimpleDateFormat(outputFormat, Locale.getDefault())
    // Create a calendar object that will convert the date and time value in milliseconds to date.
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    return formatter.format(calendar.time)
}
