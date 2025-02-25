package com.bngdev.formatk.date

import kotlinx.datetime.TimeZone

class JsDateFormatFactory(
    private val locale: String,
) : DateFormatterFactory {

    override fun getFormatter(formatOptions: DateFormatterSettings?): DateFormatter {
        return JsDateFormatter(locale, formatOptions ?: getDefaultSettings())
    }

    override fun getDefaultSettings(): DateFormatterSettings {
        return DateFormatterSettings(
            timeZone = TimeZone.UTC,
            pattern = null,
            dateStyle = FormatStyle.LONG,
            timeStyle = FormatStyle.SHORT,
            hourCycle = HourCycle.H12
        )
    }
}
