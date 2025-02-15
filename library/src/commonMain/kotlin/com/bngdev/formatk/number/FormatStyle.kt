package com.bngdev.formatk.number

enum class FormatStyle {
    DECIMAL,
    CURRENCY,
    PERCENT
}

fun FormatStyle?.orDefault() = this ?: FormatStyle.DECIMAL
