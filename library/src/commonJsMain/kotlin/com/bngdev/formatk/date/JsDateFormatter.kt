package com.bngdev.formatk.date

import com.bngdev.formatk.utils.normalizeWhitespaces
import kotlinx.datetime.Instant
import luxon.DateTime
import luxon.DateTimeOptions
import luxon.FromFormatOptions
import luxon.ToLocaleStringOptions

class JsDateFormatter(
    private val locale: String,
    private val settings: DateFormatterSettings
) : DateFormatter {

    override fun format(instant: Instant): String {
        val dateTime = DateTime.fromMillis(
            instant.toEpochMilliseconds().toDouble(), createDateTimeOptions()
        )
        return if (settings.pattern != null) {
            dateTime.toFormat(settings.pattern).normalizeWhitespaces()
        } else {
            dateTime.toLocaleString(createLocaleOptions()).normalizeWhitespaces()
        }
    }

    override fun parse(value: String): Instant? {
        if(settings.pattern == null){
            return null
        }

        val luxonDateTime = try {
            DateTime.fromFormat(value, settings.pattern, createFromFormatOptions())
        } catch (e: Throwable) {
            return null
        }

        return try {
            luxonDateTime.toMillis().let { Instant.fromEpochMilliseconds(it.toLong()) }
        } catch (e: Throwable) {
            null
        }
    }

    private fun createFromFormatOptions(): FromFormatOptions {
        val fromFormatOptions = fromFormatOptionsTemplate()
        fromFormatOptions.locale = locale
        if (settings.timeZone != null) {
            fromFormatOptions.zone = settings.timeZone.id
        }
        return fromFormatOptions
    }

    private fun createDateTimeOptions(): DateTimeOptions {
        val dateTimeOptionsTemplate = dateTimeOptionsTemplate()
        dateTimeOptionsTemplate.locale = locale
        getTimezone(settings)?.let {
            dateTimeOptionsTemplate.zone = it
        }
        return dateTimeOptionsTemplate
    }

    private fun createLocaleOptions(): ToLocaleStringOptions {
        val toLocaleStringOptions = toLocaleStringTemplate()
        settings.dateStyle.toLuxon()?.let {
            toLocaleStringOptions.dateStyle = it
        }
        settings.timeStyle.toLuxon()?.let {
            toLocaleStringOptions.timeStyle = it
        }
        toLocaleStringOptions.locale = locale
        getTimezone(settings)?.let {
            toLocaleStringOptions.timeZone = it
        }
        return toLocaleStringOptions
    }

    private fun getTimezone(settings: DateFormatterSettings): String? {
        if(settings.timeZone == null) {
            return null
        }
        if(settings.timeZone.id == "Z"){
            return "UTC"
        }
        return settings.timeZone.id
    }
}

private fun FormatStyle.toLuxon(): String? {
    return when (this) {
        FormatStyle.SHORT -> "short"
        FormatStyle.MEDIUM -> "medium"
        FormatStyle.LONG -> "long"
        FormatStyle.FULL -> "full"
        FormatStyle.NONE -> null
    }
}
