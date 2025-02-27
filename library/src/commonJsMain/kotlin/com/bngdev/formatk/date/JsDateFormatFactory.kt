package com.bngdev.formatk.date;

class JsDateFormatFactory(private val locale: String) : DateFormatterFactory {

    override fun getFormatter(formatOptions: DateFormatterSettings): DateFormatter {
        return JsDateFormatter(locale, formatOptions)
    }
}
