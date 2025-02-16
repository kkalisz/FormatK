package com.bngdev.formatk.number

class JSNumberFormat(
    private val format: Intl.NumberFormat,
) : NumberFormater {

    override fun format(value: Number): String {
        return format.format(value.toDouble())
    }
}
