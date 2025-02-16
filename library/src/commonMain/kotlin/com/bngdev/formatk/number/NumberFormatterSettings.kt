package com.bngdev.formatk.number

import com.bngdev.formatk.CurrencyInfo
import com.bngdev.formatk.LocaleInfo
import kotlin.math.min

data class NumberFormatterSettings(
    val useGrouping: Boolean? = null,
    val roundingMode: RoundingMode? = null,
    val minimumFractionDigits: Int? = null,
    val maximumFractionDigits: Int? = null,
    val currencyCode: String? = null,
)

fun NumberFormatterSettings.getMinimumFractionDigitsSafe(): Int? {
    if (maximumFractionDigits == null || minimumFractionDigits == null) {
        return minimumFractionDigits
    }
    return min(minimumFractionDigits, maximumFractionDigits)
}

fun NumberFormatterSettings.getMaximumFractionDigitsSafe(): Int? {
    if (maximumFractionDigits == null || minimumFractionDigits == null) {
        return maximumFractionDigits
    }
    return maximumFractionDigits
}

fun NumberFormatterSettings.getCurrencyInfoSafe(localeInfo: LocaleInfo): CurrencyInfo? {
    if (currencyCode != null) {
        return CurrencyInfo.getCurrencyByCode(currencyCode)
    }
    return CurrencyInfo.getCurrency(localeInfo)
}
