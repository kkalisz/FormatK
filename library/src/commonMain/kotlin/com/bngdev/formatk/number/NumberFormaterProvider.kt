package com.bngdev.formatk.number

import com.bngdev.formatk.LocaleInfo

expect object NumberFormaterProvider {

    fun getInstance(locale: LocaleInfo): NumberFormatFactory
}

fun NumberFormaterProvider.getFormater(
    locale: LocaleInfo,
    style: FormatStyle = FormatStyle.DECIMAL,
    formatOptions: NumberFormaterSettings? = null
): NumberFormater {
    return getInstance(locale).getFormatter(style, formatOptions)
}

fun NumberFormaterProvider.getDefaultSettings(
    locale: LocaleInfo,
    style: FormatStyle = FormatStyle.DECIMAL,
): NumberFormaterSettings {
    return getInstance(locale).getDefaultSettings(style)
}

