package com.bngdev.formatk.number

interface NumberFormater {
    fun format(
        value: Number,
        style: FormatStyle? = FormatStyle.DECIMAL,
        formatOptions: NumberFormaterSettings? = null,
    ): String

    fun getDefaultSettings(
        style: FormatStyle = FormatStyle.DECIMAL
    ): NumberFormaterSettings
}
