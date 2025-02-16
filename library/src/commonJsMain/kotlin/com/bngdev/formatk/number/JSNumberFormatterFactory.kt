package com.bngdev.formatk.number

import com.bngdev.formatk.CurrencyInfo
import com.bngdev.formatk.LocaleInfo

class JSNumberFormatterFactory(
    private val locale: LocaleInfo,
) : NumberFormatFactory {
    override fun getFormatter(style: FormatStyle, formatOptions: NumberFormatterSettings?): NumberFormatter {
        val options = numberFormatOptionsTemplate()
        formatOptions?.let { applySettings(it, options) }
        val systemFormat = getFormatter(style.orDefault(), options)
        return JSNumberFormat(systemFormat)
    }

    override fun getDefaultSettings(style: FormatStyle): NumberFormatterSettings {
        return createNumberFormatSettings(getFormatter(style, numberFormatOptionsTemplate()))
    }

    private fun createNumberFormatSettings(formatter: Intl.NumberFormat): NumberFormatterSettings {
        val options = formatter.resolvedOptions()
        return NumberFormatterSettings(
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

    private fun applySettings(source: NumberFormatterSettings, destination: NumberFormatOptionsJs) {
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

