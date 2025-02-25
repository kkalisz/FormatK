package com.bngdev.formatk.date

import kotlinx.datetime.Instant
import luxon.DateTime

class JsDateFormatter(
    private val locale: String, // Now accepts locale directly in the constructor
    private val settings: DateFormatterSettings
) : DateFormatter {

    override fun format(instant: Instant): String {
        val dateTime = DateTime.fromMillis(
            instant.toEpochMilliseconds().toDouble(),
            dateTimeOptionsTemplate().also {
                it.zone = settings.timeZone?.id // Apply time zone if provided
                it.locale = locale // Apply the passed locale
            }
        )

        return if (settings.pattern != null) {
            dateTime.toFormat(settings.pattern)
        } else {
            dateTime.toLocaleString(
                toLocaleStringOptionsTemplate().apply {
                    dateStyle = settings.dateStyle.toLuxon()
                    timeStyle = settings.timeStyle.toLuxon()
                    hourCycle = settings.hourCycle.toLuxon()
                    timeZone = settings.timeZone?.id
                    this.locale = locale // Apply locale here as well
                }
            )
        }
    }

    // Parsing method to convert formatted string back to Instant
    override fun parse(value: String): Instant? {
        val luxonDateTime = DateTime.fromFormat(
            value,
            settings.pattern ?: getDefaultPattern(settings),
            fromFormatOptionsTemplate().apply {
                this.locale = locale // Apply locale for parsing
            }
        )
        if (!luxonDateTime.isValid()) {
            return null
        }

        return luxonDateTime.toMillis().let { Instant.fromEpochMilliseconds(it.toLong()) }
    }

    private fun getDefaultPattern(settings: DateFormatterSettings?): String {
        val dateStyle = settings?.dateStyle ?: FormatStyle.LONG
        val timeStyle = settings?.timeStyle ?: FormatStyle.SHORT

        val datePattern = when (dateStyle) {
            FormatStyle.SHORT -> "MM/dd/yyyy"
            FormatStyle.MEDIUM -> "MMM dd, yyyy"
            FormatStyle.LONG -> "MMMM dd, yyyy"
            FormatStyle.FULL -> "EEEE, MMMM dd, yyyy"
        }

        val timePattern = when (timeStyle) {
            FormatStyle.SHORT -> "HH:mm"
            FormatStyle.MEDIUM -> "HH:mm:ss"
            FormatStyle.LONG -> "HH:mm:ss z"
            FormatStyle.FULL -> "HH:mm:ss zzzz"
        }

        return "$datePattern $timePattern"
    }
}

// Extensions to map Kotlin settings to Luxon values
private fun FormatStyle.toLuxon(): String {
    return when (this) {
        FormatStyle.SHORT -> "short"
        FormatStyle.MEDIUM -> "medium"
        FormatStyle.LONG -> "long"
        FormatStyle.FULL -> "full"
    }
}

private fun HourCycle.toLuxon(): String {
    return when (this) {
        HourCycle.H12 -> "h12"
        HourCycle.H24 -> "h23"
    }
}
