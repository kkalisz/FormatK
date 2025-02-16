package com.bngdev.formatk.number

interface NumberFormatFactory {
    fun getFormatter(
        style: FormatStyle = FormatStyle.DECIMAL,
        formatOptions: NumberFormatterSettings? = null
    ): NumberFormatter

    fun getDefaultSettings(style: FormatStyle = FormatStyle.DECIMAL): NumberFormatterSettings
}
