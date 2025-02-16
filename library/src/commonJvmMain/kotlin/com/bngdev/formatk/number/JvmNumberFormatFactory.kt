package com.bngdev.formatk.number

import com.bngdev.formatk.LocaleInfo
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

class JvmNumberFormatFactory(
    private val locale: LocaleInfo,
) : NumberFormatFactory {

    override fun getFormatter(style: FormatStyle, formatOptions: NumberFormaterSettings?): NumberFormater {
        val format = getFormatter(style.orDefault()).also { numberFormat ->
            formatOptions?.let { options -> applySettings(options, numberFormat, style.orDefault()) }
        }
        return JvmNumberFormat(format)
    }

    override fun getDefaultSettings(style: FormatStyle): NumberFormaterSettings {
        return createNumberFormatSettings(getFormatter(style))
    }

    private fun applySettings(source: NumberFormaterSettings, destination: NumberFormat, style: FormatStyle) {
        source.getMaximumFractionDigitsSafe()?.let { destination.maximumFractionDigits = it }
        source.getMinimumFractionDigitsSafe()?.let { destination.minimumFractionDigits = it }
        source.useGrouping?.let { destination.isGroupingUsed = it }
        source.roundingMode?.let { destination.roundingMode = it.toJavaRoundingMode() }
        source.currencyCode?.let { destination.currency = Currency.getInstance(it) }
        // jvm implementation may have different symbols for than expected ex: "￥" instead "¥" for JPY
        if (style == FormatStyle.CURRENCY) {
            val currencyInfo = source.getCurrencyInfoSafe(locale)
            if (currencyInfo?.symbol != null && destination is DecimalFormat) {
                destination.decimalFormatSymbols = destination.decimalFormatSymbols.apply {
                    currencySymbol = currencyInfo.symbol
                }
            }
        }
    }

    private fun RoundingMode.toJavaRoundingMode(): java.math.RoundingMode = when (this) {
        RoundingMode.UP -> java.math.RoundingMode.UP
        RoundingMode.DOWN -> java.math.RoundingMode.DOWN
        RoundingMode.CEILING -> java.math.RoundingMode.CEILING
        RoundingMode.FLOOR -> java.math.RoundingMode.FLOOR
        RoundingMode.HALF_UP -> java.math.RoundingMode.HALF_UP
        RoundingMode.HALF_DOWN -> java.math.RoundingMode.HALF_DOWN
        RoundingMode.HALF_EVEN -> java.math.RoundingMode.HALF_EVEN
    }

    private fun createNumberFormatSettings(format: NumberFormat): NumberFormaterSettings {
        return NumberFormaterSettings(
            useGrouping = format.isGroupingUsed,
            roundingMode = format.roundingMode.toFormatkRoundingMode(),
            minimumFractionDigits = format.minimumFractionDigits,
            maximumFractionDigits = format.maximumFractionDigits,
            currencyCode = format.currency?.currencyCode,
        )
    }

    private fun getFormatter(style: FormatStyle): NumberFormat {
        val inLocale = locale.toLocale()
        return when (style) {
            FormatStyle.DECIMAL -> DecimalFormat.getInstance(inLocale)
            FormatStyle.CURRENCY -> NumberFormat.getCurrencyInstance(inLocale)
            FormatStyle.PERCENT -> NumberFormat.getPercentInstance(inLocale)
        }
    }

    // TODO check version of java if possible take into consideration android  (Locale.of)
    private fun LocaleInfo.toLocale(): Locale = Locale(this.language, this.region)

    private fun java.math.RoundingMode.toFormatkRoundingMode(): RoundingMode = when (this) {
        java.math.RoundingMode.UP -> RoundingMode.UP
        java.math.RoundingMode.DOWN -> RoundingMode.DOWN
        java.math.RoundingMode.CEILING -> RoundingMode.CEILING
        java.math.RoundingMode.FLOOR -> RoundingMode.FLOOR
        java.math.RoundingMode.HALF_UP -> RoundingMode.HALF_UP
        java.math.RoundingMode.HALF_DOWN -> RoundingMode.HALF_DOWN
        java.math.RoundingMode.HALF_EVEN -> RoundingMode.HALF_EVEN
        java.math.RoundingMode.UNNECESSARY -> RoundingMode.HALF_EVEN // UNNECESSARY is only available in jvm
    }
}
