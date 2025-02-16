package com.bngdev.formatk.number

@Suppress("unused")
external class ResolvedNumberFormatOptions {
    val locale: String
    val numberingSystem: String
    val style: String
    val currency: String?
    val currencyDisplay: String?
    val minimumIntegerDigits: Int
    val minimumFractionDigits: Int?
    val maximumFractionDigits: Int?
    val minimumSignificantDigits: Int?
    val maximumSignificantDigits: Int?
    val roundingMode: String?
    val useGrouping: String?
}

@Suppress("unused")
external interface NumberFormatOptionsJs {
    var localeMatcher: String?
    var style: String?
    var currency: String?
    var currencyDisplay: String?
    var useGrouping: Boolean?
    var minimumIntegerDigits: Int?
    var minimumFractionDigits: Int?
    var maximumFractionDigits: Int?
    var minimumSignificantDigits: Int?
    var maximumSignificantDigits: Int?
    var roundingMode: String?
}

@Suppress("unused")
external object Intl {
    class NumberFormat(
        locales: String,
        options: NumberFormatOptionsJs,
    ) {
        fun format(value: Double): String

        fun resolvedOptions(): ResolvedNumberFormatOptions
    }
}
