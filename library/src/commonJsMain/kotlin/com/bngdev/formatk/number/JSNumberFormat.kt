package com.bngdev.formatk.number

import com.bngdev.formatk.CurrencyInfo
import com.bngdev.formatk.LocaleInfo

class JsNumberFormat(
    private val locale: LocaleInfo,
) : NumberFormater {

    override fun getDefaultSettings(style: FormatStyle): NumberFormaterSettings {
        return createNumberFormatSettings(getFormatter(style, numberFormatOptionsTemplate()))
    }

    override fun format(value: Number, style: FormatStyle?, formatOptions: NumberFormaterSettings?): String {
        val options = numberFormatOptionsTemplate()
        formatOptions?.let { applySettings(it, options) }
        val systemFormat = getFormatter(style.orDefault(), options)
        return systemFormat.format(value.toDouble())
    }

    private fun createNumberFormatSettings(formatter: Intl.NumberFormat): NumberFormaterSettings {
        val options = formatter.resolvedOptions()
        return NumberFormaterSettings(
            roundingMode = RoundingMode.HALF_EVEN,
            minimumFractionDigits = options.minimumFractionDigits,
            maximumFractionDigits = options.maximumFractionDigits,
            currencyCode = options.currency,
            useGrouping = mapUseGroupingJsToBoolean(options.useGrouping),
        )
    }

    private fun getFormatter(formatStyle: FormatStyle, options: NumberFormatOptionsJs): Intl.NumberFormat {
        val inLocale = locale.toLanguageTag()
        options.style = formatStyle.toIntlFormatterStyle()
        applyCurrencyIfNeeded(options, formatStyle)
        return Intl.NumberFormat(
            locales = inLocale,
            options = options,
        )
    }

    private fun FormatStyle.toIntlFormatterStyle(): String {
        return when (this) {
            FormatStyle.DECIMAL -> "decimal"
            FormatStyle.CURRENCY -> "currency"
            FormatStyle.PERCENT -> "percent"
        }
    }

    private fun applyCurrencyIfNeeded(options: NumberFormatOptionsJs, formatStyle: FormatStyle) {
        if (options.currency != null) {
            return
        }
        if (formatStyle == FormatStyle.CURRENCY) {
            val info = CurrencyInfo.getCurrency(locale)
            options.currency = info?.code
            options.currencyDisplay = "narrowSymbol"
        }
    }

    private fun applySettings(source: NumberFormaterSettings, destination: NumberFormatOptionsJs) {
        source.getMaximumFractionDigitsSafe()?.let { destination.maximumFractionDigits = it }
        source.getMinimumFractionDigitsSafe()?.let { destination.minimumFractionDigits = it }
        source.useGrouping?.let { destination.useGrouping = it }
        source.roundingMode?.let { destination.roundingMode = mapRoundingModeToIntl(it) }
        source.currencyCode?.let { destination.currency = it }
    }

    private fun mapUseGroupingJsToBoolean(useGrouping: String?): Boolean? {
        return when (useGrouping) {
            "auto" -> true
            "always" -> true
            "never" -> false
            else -> null
        }
    }

    private fun mapRoundingModeToIntl(mode: RoundingMode?): String {
        return when (mode) {
            RoundingMode.CEILING -> "ceil"
            RoundingMode.FLOOR -> "floor"
            RoundingMode.UP -> "expand"
            RoundingMode.DOWN -> "trunc"
            RoundingMode.HALF_UP -> "halfExpand"
            RoundingMode.HALF_DOWN -> "halfTrunc"
            RoundingMode.HALF_EVEN -> "halfEven"
            null -> "halfEven"
        }
    }
}

@Suppress("unused")
private external class ResolvedNumberFormatOptions {
    val locale: String
    val numberingSystem: String
    val style: String // NumberFormatOptionsStyle
    val currency: String?
    val currencyDisplay: String? // NumberFormatOptionsCurrencyDisplay
    val minimumIntegerDigits: Int
    val minimumFractionDigits: Int?
    val maximumFractionDigits: Int?
    val minimumSignificantDigits: Int?
    val maximumSignificantDigits: Int?
    val roundingMode: String?
    val useGrouping: String? // ResolvedNumberFormatOptionsUseGrouping (Boolean or String)
}

@Suppress("unused")
external interface NumberFormatOptionsJs {
    var localeMatcher: String?
    var style: String?
    var currency: String?
    var currencyDisplay: String? // NumberFormatOptionsCurrencyDisplay
    var useGrouping: Boolean? // NumberFormatOptionsUseGrouping (Boolean or String)
    var minimumIntegerDigits: Int?
    var minimumFractionDigits: Int?
    var maximumFractionDigits: Int?
    var minimumSignificantDigits: Int?
    var maximumSignificantDigits: Int?
    var roundingMode: String?
}

@Suppress("unused")
private external object Intl {
    class NumberFormat(
        locales: String,
        options: NumberFormatOptionsJs,
    ) {
        fun format(value: Double): String

        fun resolvedOptions(): ResolvedNumberFormatOptions
    }
}

actual object NumberFormaterFactory {
    actual fun getInstance(locale: LocaleInfo): NumberFormater {
        return JsNumberFormat(locale)
    }
}
