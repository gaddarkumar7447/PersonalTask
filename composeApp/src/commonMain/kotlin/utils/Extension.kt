package utils

import kotlinx.datetime.LocalDateTime

fun String.convertDateTime() : String{
    val dateTime = LocalDateTime.parse(this)
    val dayOfWeek = dateTime.dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() }
    val dayOfMonth = dateTime.dayOfMonth
    val month = dateTime.month.name.lowercase().replaceFirstChar { it.uppercase() }
    val year = dateTime.year
    val hour = if (dateTime.hour % 12 == 0) 12 else dateTime.hour % 12
    val minute = dateTime.minute.toString().padStart(2, '0')
    val period = if (dateTime.hour < 12) "AM" else "PM"

    return "$dayOfWeek, $dayOfMonth $month $year, $hour:$minute$period"
}