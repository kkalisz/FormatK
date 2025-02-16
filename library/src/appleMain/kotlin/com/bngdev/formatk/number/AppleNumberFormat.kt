package com.bngdev.formatk.number

import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter

class AppleNumberFormat(private val formatter: NSNumberFormatter) : NumberFormater {

    override fun format(
        value: Number,
    ): String {
        @Suppress("CAST_NEVER_SUCCEEDS")
        return formatter.stringFromNumber(value as NSNumber) ?: value.toString()
    }
}
