package com.bngdev.formatk.number

interface NumberFormatFactory {
    fun getFormatter(
        style: FormatStyle = FormatStyle.DECIMAL,
        formatOptions: NumberFormaterSettings? = null
    ): NumberFormater

    fun getDefaultSettings(style: FormatStyle = FormatStyle.DECIMAL): NumberFormaterSettings
}
