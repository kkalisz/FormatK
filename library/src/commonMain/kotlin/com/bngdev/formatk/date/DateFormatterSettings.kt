package com.bngdev.formatk.date

import com.bngdev.formatk.date.DateTimeFormatMode.DATE_ONLY
import com.bngdev.formatk.date.DateTimeFormatMode.DATE_TIME
import com.bngdev.formatk.date.DateTimeFormatMode.TIME_ONLY
import kotlinx.datetime.TimeZone

data class DateFormatterSettings(
    val timeZone: TimeZone? = null, // Time zone (e.g., "UTC", "America/New_York")
    val pattern: String? = null, // Custom pattern
    val dateStyle: FormatStyle = FormatStyle.LONG, // Date formatting style
    val timeStyle: FormatStyle = FormatStyle.SHORT, // Time formatting style
)

fun DateFormatterSettings.getFormatMode(): DateTimeFormatMode {
    if (dateStyle != FormatStyle.NONE) {
        if (timeStyle != FormatStyle.NONE) {
            return DATE_TIME
        }
        return DATE_ONLY
    } else {
        if (timeStyle != FormatStyle.NONE) {
            return TIME_ONLY
        }
        return DATE_TIME // TODO
    }
}
