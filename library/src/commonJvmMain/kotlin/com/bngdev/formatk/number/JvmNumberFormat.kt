package com.bngdev.formatk.number

import java.math.BigDecimal
import java.text.NumberFormat

class JvmNumberFormat(private val numberFormat: NumberFormat) : NumberFormater {
    override fun format(value: Number): String {
        if (value is Double || value is Float) {
            return numberFormat.format(BigDecimal.valueOf(value.toDouble()))
        }
        return numberFormat.format(value)
    }
}
