package com.bngdev.formatk.number

import com.bngdev.formatk.CurrencyInfo
import com.bngdev.formatk.LocaleInfo
import kotlin.math.min

data class NumberFormaterSettings(
    val useGrouping: Boolean? = null,
    val roundingMode: RoundingMode? = null,
    val minimumFractionDigits: Int? = null,
    val maximumFractionDigits: Int? = null,
    val currencyCode: String? = null,
)

fun NumberFormaterSettings.getMinimumFractionDigitsSafe(): Int? {
    if (maximumFractionDigits == null || minimumFractionDigits == null) {
        return minimumFractionDigits
    }
    return min(minimumFractionDigits, maximumFractionDigits)
}

fun NumberFormaterSettings.getMaximumFractionDigitsSafe(): Int? {
    if (maximumFractionDigits == null || minimumFractionDigits == null) {
        return maximumFractionDigits
    }
    return maximumFractionDigits
}

fun NumberFormaterSettings.getCurrencyInfoSafe(localeInfo: LocaleInfo): CurrencyInfo? {
    if (currencyCode != null) {
        println("get for $currencyCode")
        return CurrencyInfo.getCurrencyByCode(currencyCode)
    }
    println("get for local $localeInfo")
    return CurrencyInfo.getCurrency(localeInfo)
}
