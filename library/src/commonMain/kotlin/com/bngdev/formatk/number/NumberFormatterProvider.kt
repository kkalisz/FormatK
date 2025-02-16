package com.bngdev.formatk.number

import com.bngdev.formatk.LocaleInfo

expect object NumberFormatterProvider {

    fun getInstance(locale: LocaleInfo): NumberFormatFactory
}

fun NumberFormatterProvider.getFormater(
    locale: LocaleInfo,
    style: FormatStyle = FormatStyle.DECIMAL,
    formatOptions: NumberFormatterSettings? = null
): NumberFormatter {
    return getInstance(locale).getFormatter(style, formatOptions)
}

fun NumberFormatterProvider.getDefaultSettings(
    locale: LocaleInfo,
    style: FormatStyle = FormatStyle.DECIMAL,
): NumberFormatterSettings {
    return getInstance(locale).getDefaultSettings(style)
}

