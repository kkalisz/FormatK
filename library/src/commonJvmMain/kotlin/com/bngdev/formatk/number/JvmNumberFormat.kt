package com.bngdev.formatk.number

import com.bngdev.formatk.LocaleInfo
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale
import java.math.RoundingMode as JavaRoundingMode

class JvmNumberFormat(private val locale: LocaleInfo) : NumberFormater {

    override fun format(
        value: Number,
        style: FormatStyle?,
        formatOptions: NumberFormaterSettings?
    ): String {
        val format = getFormatter(style.orDefault())
        formatOptions?.let { options -> applySettings(options, format, style.orDefault()) }
        // we have different methods for int and floating point values
        if (value is Double || value is Float) {
            return format.format(BigDecimal.valueOf(value.toDouble()))
        }
        return format.format(value)
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

    private fun RoundingMode.toJavaRoundingMode(): JavaRoundingMode = when (this) {
        RoundingMode.UP -> JavaRoundingMode.UP
        RoundingMode.DOWN -> JavaRoundingMode.DOWN
        RoundingMode.CEILING -> JavaRoundingMode.CEILING
        RoundingMode.FLOOR -> JavaRoundingMode.FLOOR
        RoundingMode.HALF_UP -> JavaRoundingMode.HALF_UP
        RoundingMode.HALF_DOWN -> JavaRoundingMode.HALF_DOWN
        RoundingMode.HALF_EVEN -> JavaRoundingMode.HALF_EVEN
    }

    private fun JavaRoundingMode.toFormatkRoundingMode(): RoundingMode = when (this) {
        JavaRoundingMode.UP -> RoundingMode.UP
        JavaRoundingMode.DOWN -> RoundingMode.DOWN
        JavaRoundingMode.CEILING -> RoundingMode.CEILING
        JavaRoundingMode.FLOOR -> RoundingMode.FLOOR
        JavaRoundingMode.HALF_UP -> RoundingMode.HALF_UP
        JavaRoundingMode.HALF_DOWN -> RoundingMode.HALF_DOWN
        JavaRoundingMode.HALF_EVEN -> RoundingMode.HALF_EVEN
        JavaRoundingMode.UNNECESSARY -> RoundingMode.HALF_EVEN // UNNECESSARY is only available in jvm
    }
}

actual object NumberFormaterFactory {
    actual fun getInstance(locale: LocaleInfo): NumberFormater {
        return JvmNumberFormat(locale)
    }
}
