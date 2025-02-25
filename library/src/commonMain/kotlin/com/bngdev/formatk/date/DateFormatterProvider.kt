package com.bngdev.formatk.date

import com.bngdev.formatk.LocaleInfo

expect object DateFormatterProvider {

    fun getInstance(locale: LocaleInfo): DateFormatterFactory
}

fun DateFormatterProvider.getFormatter(
    locale: LocaleInfo,
    formatOptions: DateFormatterSettings = DateFormatterSettings()
): DateFormatter {
    return getInstance(locale).getFormatter(formatOptions)
}