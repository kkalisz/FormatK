package com.bngdev.formatk.number

import com.bngdev.formatk.LocaleInfo
import platform.Foundation.NSLocale
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterCurrencyStyle
import platform.Foundation.NSNumberFormatterDecimalStyle
import platform.Foundation.NSNumberFormatterPercentStyle
import platform.Foundation.NSNumberFormatterRoundCeiling
import platform.Foundation.NSNumberFormatterRoundDown
import platform.Foundation.NSNumberFormatterRoundFloor
import platform.Foundation.NSNumberFormatterRoundHalfDown
import platform.Foundation.NSNumberFormatterRoundHalfEven
import platform.Foundation.NSNumberFormatterRoundHalfUp
import platform.Foundation.NSNumberFormatterRoundUp
import platform.Foundation.NSNumberFormatterRoundingMode
import platform.Foundation.NSNumberFormatterStyle

class AppleNumberFormatFactory(private val locale: LocaleInfo) : NumberFormatFactory {

    override fun getFormatter(style: FormatStyle, formatOptions: NumberFormatterSettings?): NumberFormatter {
        val formatter = getFormatter(style)
        formatOptions?.let { options -> applySettings(options, formatter) }
        return AppleNumberFormat(formatter)
    }

    override fun getDefaultSettings(style: FormatStyle): NumberFormatterSettings {
        return createNumberFormatSettings(getFormatter(style))
    }

    private fun getFormatter(style: FormatStyle): NSNumberFormatter {
        val formatter = NSNumberFormatter().also {
            it.locale = locale.toNSLocale()
            it.numberStyle = toNSNumberFormatterStyle(style)
        }
        return formatter
    }

    private fun createNumberFormatSettings(formatter: NSNumberFormatter): NumberFormatterSettings {
        return NumberFormatterSettings(
            roundingMode = formatter.roundingMode.toFormatkRoundingMode(),
            minimumFractionDigits = formatter.minimumFractionDigits.toInt(),
            maximumFractionDigits = formatter.maximumFractionDigits.toInt(),
            currencyCode = formatter.currencyCode,
            useGrouping = formatter.usesGroupingSeparator,
        )
    }

    private fun applySettings(source: NumberFormatterSettings, destination: NSNumberFormatter) {
        source.useGrouping?.let { destination.usesGroupingSeparator = it }
        source.getMaximumFractionDigitsSafe()?.let { destination.maximumFractionDigits = it.toULong() }
        source.getMinimumFractionDigitsSafe()?.let { destination.minimumFractionDigits = it.toULong() }
        source.roundingMode?.let { destination.roundingMode = it.toNSNumberFormatterRoundingMode() }
        source.currencyCode?.let { destination.currencyCode = it }
        // implementations may have different symbols for than expected ex: "￥" instead "¥" for JPY
        if (destination.numberStyle == NSNumberFormatterCurrencyStyle) {
            val currencyInfo = source.getCurrencyInfoSafe(locale)
            if (currencyInfo?.symbol != null) {
                destination.currencySymbol = currencyInfo.symbol
            }
        }
    }

    private fun NSNumberFormatterRoundingMode.toFormatkRoundingMode(): RoundingMode {
        return when (this) {
            NSNumberFormatterRoundUp -> RoundingMode.UP
            NSNumberFormatterRoundDown -> RoundingMode.DOWN
            NSNumberFormatterRoundCeiling -> RoundingMode.CEILING
            NSNumberFormatterRoundFloor -> RoundingMode.FLOOR
            NSNumberFormatterRoundHalfUp -> RoundingMode.HALF_UP
            NSNumberFormatterRoundHalfDown -> RoundingMode.HALF_DOWN
            NSNumberFormatterRoundHalfEven -> RoundingMode.HALF_EVEN
            else -> RoundingMode.HALF_UP
        }
    }

    private fun RoundingMode.toNSNumberFormatterRoundingMode(): NSNumberFormatterRoundingMode {
        return when (this) {
            RoundingMode.UP -> NSNumberFormatterRoundUp
            RoundingMode.DOWN -> NSNumberFormatterRoundDown
            RoundingMode.CEILING -> NSNumberFormatterRoundCeiling
            RoundingMode.FLOOR -> NSNumberFormatterRoundFloor
            RoundingMode.HALF_UP -> NSNumberFormatterRoundHalfUp
            RoundingMode.HALF_DOWN -> NSNumberFormatterRoundHalfDown
            RoundingMode.HALF_EVEN -> NSNumberFormatterRoundHalfEven
        }
    }

    private fun toNSNumberFormatterStyle(style: FormatStyle): NSNumberFormatterStyle {
        return when (style) {
            FormatStyle.DECIMAL -> NSNumberFormatterDecimalStyle
            FormatStyle.CURRENCY -> NSNumberFormatterCurrencyStyle
            FormatStyle.PERCENT -> NSNumberFormatterPercentStyle
        }
    }
}

fun LocaleInfo.toNSLocale(): NSLocale = NSLocale(localeIdentifier = "${this.language}_${this.region}")
