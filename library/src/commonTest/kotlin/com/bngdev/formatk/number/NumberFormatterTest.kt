package com.bngdev.formatk.number

import com.bngdev.formatk.LocaleInfo
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class NumberFormatterTest {
    companion object {
        const val NBSP = " "
        const val NNBSP = " "
    }

    @Test
    fun `test formatting decimal value without options in US locale`() {
        val localeInfo = LocaleInfo("en", "US")
        val format = NumberFormaterFactory.getInstance(localeInfo)

        val result = format.format(1234.567, FormatStyle.DECIMAL, null)

        assertEquals("1,234.567", result)
    }

    @Test
    fun `test formatting decimal value without options in German locale`() {
        val localeInfo = LocaleInfo("de", "DE")
        val format = NumberFormaterFactory.getInstance(localeInfo)

        val result = format.format(1234.567, FormatStyle.DECIMAL, null)

        assertEquals("1.234,567", result)
    }

    @Test
    fun `test formatting decimal value with custom fraction digits in French locale`() {
        val localeInfo = LocaleInfo("fr", "FR")
        val format = NumberFormaterFactory.getInstance(localeInfo)
        val formatOptions = NumberFormaterSettings(minimumFractionDigits = 2, maximumFractionDigits = 2)

        val result = format.format(1234.567, FormatStyle.DECIMAL, formatOptions)

        assertEquals("1${NNBSP}234,57", result)
    }

    @Test
    fun `test formatting currency value for US locale`() {
        val localeInfo = LocaleInfo("en", "US")
        val format = NumberFormaterFactory.getInstance(localeInfo)

        val result = format.format(1234.5, FormatStyle.CURRENCY, null)

        assertEquals("$1,234.50", result)
    }

    @Test
    fun `test formatting currency value for Japanese locale`() {
        val localeInfo = LocaleInfo("ja", "JP")
        val format = NumberFormaterFactory.getInstance(localeInfo)

        val result = format.format(1234.51, FormatStyle.CURRENCY, NumberFormaterSettings(currencyCode = "JPY"))

        assertEquals("￥1,235", result)
    }

    @Test
    fun `test formatting percentage value for US locale`() {
        val localeInfo = LocaleInfo("en", "US")
        val format = NumberFormaterFactory.getInstance(localeInfo)

        val result = format.format(0.85, FormatStyle.PERCENT, null)

        assertEquals("85%", result)
    }

    @Test
    fun `test formatting percentage value for German locale`() {
        val localeInfo = LocaleInfo("de", "DE")
        val format = NumberFormaterFactory.getInstance(localeInfo)

        val result = format.format(0.85, FormatStyle.PERCENT, null)

        assertEquals("85$NBSP%", result)
    }

    @Test
    fun `test applying rounding mode HALF_UP for decimal style`() {
        val localeInfo = LocaleInfo("en", "US")
        val format = NumberFormaterFactory.getInstance(localeInfo)
        val formatOptions = NumberFormaterSettings(
            maximumFractionDigits = 1,
            roundingMode = RoundingMode.HALF_UP
        )

        val result = format.format(123.44, FormatStyle.DECIMAL, formatOptions)

        assertEquals("123.4", result)
    }

    @Test
    fun `test applying rounding mode HALF_DOWN for decimal style`() {
        val localeInfo = LocaleInfo("en", "US")
        val format = NumberFormaterFactory.getInstance(localeInfo)
        val formatOptions = NumberFormaterSettings(
            maximumFractionDigits = 1,
            roundingMode = RoundingMode.HALF_DOWN
        )

        val result = format.format(123.45, FormatStyle.DECIMAL, formatOptions)

        assertEquals("123.4", result)
    }

    @Test
    fun `test currency formatting with custom currency code in US locale`() {
        val localeInfo = LocaleInfo("en", "US")
        val format = NumberFormaterFactory.getInstance(localeInfo)
        val formatOptions = NumberFormaterSettings(currencyCode = "EUR")

        val result = format.format(1234.56, FormatStyle.CURRENCY, formatOptions)

        assertTrue(result.contains("€"))
    }

    @Test
    fun `test currency formatting with custom currency code in German locale`() {
        val localeInfo = LocaleInfo("de", "DE")
        val format = NumberFormaterFactory.getInstance(localeInfo)
        val formatOptions = NumberFormaterSettings(currencyCode = "GBP")

        val result = format.format(1234.56, FormatStyle.CURRENCY, formatOptions)

        assertTrue(result.contains("£"))
    }

    @Test
    fun `test default settings for decimal style in US locale`() {
        val localeInfo = LocaleInfo("en", "US")
        val format = NumberFormaterFactory.getInstance(localeInfo)

        val defaultSettings = format.getDefaultSettings(FormatStyle.DECIMAL)

        assertTrue(defaultSettings.useGrouping!!)
        assertEquals(RoundingMode.HALF_EVEN, defaultSettings.roundingMode)
    }

    @Test
    fun `test default settings for decimal style in Italian locale`() {
        val localeInfo = LocaleInfo("it", "IT")
        val format = NumberFormaterFactory.getInstance(localeInfo)

        val defaultSettings = format.getDefaultSettings(FormatStyle.DECIMAL)

        assertTrue(defaultSettings.useGrouping!!)
        assertEquals(RoundingMode.HALF_EVEN, defaultSettings.roundingMode)
    }

    @Test
    fun `test grouping settings applied`() {
        val localeInfo = LocaleInfo("en", "US")
        val format = NumberFormaterFactory.getInstance(localeInfo)
        val formatOptions = NumberFormaterSettings(useGrouping = false)

        val result = format.format(1234567.89, FormatStyle.DECIMAL, formatOptions)

        assertEquals("1234567.89", result)
    }

    @Test
    fun `test no grouping and custom fraction digits in French locale`() {
        val localeInfo = LocaleInfo("fr", "FR")
        val format = NumberFormaterFactory.getInstance(localeInfo)
        val formatOptions = NumberFormaterSettings(
            useGrouping = false,
            minimumFractionDigits = 3,
            maximumFractionDigits = 3
        )

        val result = format.format(1234.567, FormatStyle.DECIMAL, formatOptions)

        assertEquals("1234,567", result)
    }

    @Test
    fun `test rounding mode CEILING`() {
        val localeInfo = LocaleInfo("en", "US")
        val format = NumberFormaterFactory.getInstance(localeInfo)
        val formatOptions = NumberFormaterSettings(
            maximumFractionDigits = 1,
            roundingMode = RoundingMode.CEILING
        )

        val result = format.format(123.41, FormatStyle.DECIMAL, formatOptions)

        assertEquals("123.5", result)
    }

    @Test
    fun `test rounding mode FLOOR`() {
        val localeInfo = LocaleInfo("en", "US")
        val format = NumberFormaterFactory.getInstance(localeInfo)
        val formatOptions = NumberFormaterSettings(
            maximumFractionDigits = 1,
            roundingMode = RoundingMode.FLOOR
        )

        val result = format.format(123.49, FormatStyle.DECIMAL, formatOptions)

        assertEquals("123.4", result)
    }

    @Test
    fun `test formatting different number types`() {
        val format = NumberFormaterFactory.getInstance(LocaleInfo("en", "US"))

        assertEquals("123", format.format(123, FormatStyle.DECIMAL))
        assertEquals("123.45", format.format(123.45f, FormatStyle.DECIMAL))
        assertEquals("123.45", format.format(123.45, FormatStyle.DECIMAL))
        assertEquals("123", format.format(123L, FormatStyle.DECIMAL))
    }

    @Test
    fun `test formatting with different styles`() {
        val format = NumberFormaterFactory.getInstance(LocaleInfo("en", "US"))
        val value = 0.456

        assertEquals("0.456", format.format(value, FormatStyle.DECIMAL))
        assertEquals("$0.46", format.format(value, FormatStyle.CURRENCY))
        assertEquals("46%", format.format(value, FormatStyle.PERCENT))
    }

    @Test
    @Ignore
    fun `test formatting with extreme values`() {
        val format = NumberFormaterFactory.getInstance(LocaleInfo("en", "US"))

        // assertEquals("1.23E8", format.format(123456789.0, FormatStyle.DECIMAL))
        assertEquals("0", format.format(0.000000001, FormatStyle.DECIMAL))
        assertEquals("∞", format.format(Double.POSITIVE_INFINITY, FormatStyle.DECIMAL))
        assertEquals("-∞", format.format(Double.NEGATIVE_INFINITY, FormatStyle.DECIMAL))
        assertEquals("NaN", format.format(Double.NaN, FormatStyle.DECIMAL))
    }

    @Test
    fun `test formatting with grouping`() {
        val format = NumberFormaterFactory.getInstance(LocaleInfo("en", "US"))
        val value = 1234567.89

        val withoutGrouping = NumberFormaterSettings(useGrouping = false)

        assertEquals("1234567.89", format.format(value, FormatStyle.DECIMAL, withoutGrouping))
    }

    @Test
    fun `test formatting without grouping`() {
        val format = NumberFormaterFactory.getInstance(LocaleInfo("en", "US"))
        val value = 1234567.89

        val withoutGrouping = NumberFormaterSettings(useGrouping = false)

        assertEquals("1234567.89", format.format(value, FormatStyle.DECIMAL, withoutGrouping))
    }

    @Test
    fun `test formatting with different rounding modes`() {
        val format = NumberFormaterFactory.getInstance(LocaleInfo("en", "US"))
        val value = 123.456
        val options = NumberFormaterSettings(
            maximumFractionDigits = 2,
            minimumFractionDigits = 2
        )

        val roundUp = options.copy(roundingMode = RoundingMode.UP)
        val roundDown = options.copy(roundingMode = RoundingMode.DOWN)
        val roundHalfUp = options.copy(roundingMode = RoundingMode.HALF_UP)

        assertEquals("123.46", format.format(value, FormatStyle.DECIMAL, roundUp))
        assertEquals("123.45", format.format(value, FormatStyle.DECIMAL, roundDown))
        assertEquals("123.46", format.format(value, FormatStyle.DECIMAL, roundHalfUp))
    }

    @Test
    @Ignore
    fun `test formatting with different currency codes`() {
        val format = NumberFormaterFactory.getInstance(LocaleInfo("en", "US"))
        val value = 123.45

        val usdOptions = NumberFormaterSettings(currencyCode = "USD")
        val eurOptions = NumberFormaterSettings(currencyCode = "EUR")
        val jpyOptions = NumberFormaterSettings(currencyCode = "JPY")

        assertEquals("$123.45", format.format(value, FormatStyle.CURRENCY, usdOptions))
        assertEquals("€123.45", format.format(value, FormatStyle.CURRENCY, eurOptions))
        assertEquals("￥123.45", format.format(value, FormatStyle.CURRENCY, jpyOptions))
    }

    @Test
    fun `test default settings for different styles`() {
        val format = NumberFormaterFactory.getInstance(LocaleInfo("en", "US"))

        val decimalSettings = format.getDefaultSettings(FormatStyle.DECIMAL)
        val currencySettings = format.getDefaultSettings(FormatStyle.CURRENCY)
        val percentSettings = format.getDefaultSettings(FormatStyle.PERCENT)

        assertTrue(decimalSettings.useGrouping == true)
        assertEquals(RoundingMode.HALF_EVEN, decimalSettings.roundingMode)
        assertEquals("USD", currencySettings.currencyCode)
        assertTrue(percentSettings.maximumFractionDigits!! >= 0)
    }

    @Test
    fun `test formatting with null style defaults to decimal`() {
        val format = NumberFormaterFactory.getInstance(LocaleInfo("en", "US"))
        val value = 123.45

        assertEquals(
            format.format(value, FormatStyle.DECIMAL),
            format.format(value, null)
        )
    }

    @Ignore
    @Test
    fun `test formatting with invalid currency code`() {
        val format = NumberFormaterFactory.getInstance(LocaleInfo("en", "US"))
        val options = NumberFormaterSettings(currencyCode = "INVALID")

        assertFailsWith<IllegalArgumentException> {
            format.format(123.45, FormatStyle.CURRENCY, options)
        }
    }

    @Test
    fun `test formatting negative numbers with different styles`() {
        val format = NumberFormaterFactory.getInstance(LocaleInfo("en", "US"))
        val value = -123.45

        assertEquals("-123.45", format.format(value, FormatStyle.DECIMAL))
        assertEquals("-$123.45", format.format(value, FormatStyle.CURRENCY))
        assertEquals("-12,345%", format.format(value, FormatStyle.PERCENT))
    }

    @Test
    fun `test formatting with zero values`() {
        val format = NumberFormaterFactory.getInstance(LocaleInfo("en", "US"))

        assertEquals("0", format.format(0, FormatStyle.DECIMAL))
        assertEquals("$0.00", format.format(0.0, FormatStyle.CURRENCY))
        assertEquals("0%", format.format(0, FormatStyle.PERCENT))
    }

    @Test
    fun `test formatting with exact rounding boundaries`() {
        val format = NumberFormaterFactory.getInstance(LocaleInfo("en", "US"))
        val options = NumberFormaterSettings(
            maximumFractionDigits = 1,
            minimumFractionDigits = 1
        )

        assertEquals(
            "1.5",
            format.format(1.45, FormatStyle.DECIMAL, options.copy(roundingMode = RoundingMode.HALF_UP))
        )
        assertEquals(
            "1.4",
            format.format(1.45, FormatStyle.DECIMAL, options.copy(roundingMode = RoundingMode.HALF_DOWN))
        )
        assertEquals(
            "1.4",
            format.format(1.45, FormatStyle.DECIMAL, options.copy(roundingMode = RoundingMode.DOWN))
        )
        assertEquals("1.5", format.format(1.45, FormatStyle.DECIMAL, options.copy(roundingMode = RoundingMode.UP)))
    }

    @Test
    fun `test formatting with various fraction digit settings`() {
        val format = NumberFormaterFactory.getInstance(LocaleInfo("en", "US"))
        val value = 123.456789

        val noFraction = NumberFormaterSettings(
            minimumFractionDigits = 0,
            maximumFractionDigits = 0
        )
        val exactFraction = NumberFormaterSettings(
            minimumFractionDigits = 3,
            maximumFractionDigits = 3
        )
        val rangeFraction = NumberFormaterSettings(
            minimumFractionDigits = 2,
            maximumFractionDigits = 4
        )

        assertEquals("123", format.format(value, FormatStyle.DECIMAL, noFraction))
        assertEquals("123.457", format.format(value, FormatStyle.DECIMAL, exactFraction))
        assertEquals("123.4568", format.format(value, FormatStyle.DECIMAL, rangeFraction))
    }

    @Test
    fun `test formatting with different locales and separators`() {
        val value = 1234567.89

        val usFormat = NumberFormaterFactory.getInstance(LocaleInfo("en", "US"))
        val deFormat = NumberFormaterFactory.getInstance(LocaleInfo("de", "DE"))
        val frFormat = NumberFormaterFactory.getInstance(LocaleInfo("fr", "FR"))
        val itFormat = NumberFormaterFactory.getInstance(LocaleInfo("it", "IT"))

        assertEquals("1,234,567.89", usFormat.format(value, FormatStyle.DECIMAL))
        assertEquals("1.234.567,89", deFormat.format(value, FormatStyle.DECIMAL))
        assertEquals("1${NNBSP}234${NNBSP}567,89", frFormat.format(value, FormatStyle.DECIMAL))
        assertEquals("1.234.567,89", itFormat.format(value, FormatStyle.DECIMAL))
    }

    @Test
    fun `test formatting with combination of all settings`() {
        val format = NumberFormaterFactory.getInstance(LocaleInfo("en", "US"))
        val value = 12345.6789

        val complexSettings = NumberFormaterSettings(
            useGrouping = true,
            roundingMode = RoundingMode.HALF_UP,
            minimumFractionDigits = 2,
            maximumFractionDigits = 3,
            currencyCode = "EUR"
        )

        assertEquals("12,345.679", format.format(value, FormatStyle.DECIMAL, complexSettings))
        assertEquals("€12,345.679", format.format(value, FormatStyle.CURRENCY, complexSettings))
        assertEquals("1,234,567.89%", format.format(value, FormatStyle.PERCENT, complexSettings))
    }

    @Test
    fun `test formatting edge numbers`() {
        val format = NumberFormaterFactory.getInstance(LocaleInfo("en", "US"))

        assertEquals("0.001", format.format(0.001, FormatStyle.DECIMAL))
        assertEquals("1,000,000", format.format(1000000, FormatStyle.DECIMAL))
        assertEquals(
            "0.1%",
            format.format(
                0.001,
                FormatStyle.PERCENT,
                NumberFormaterSettings(minimumFractionDigits = 1, maximumFractionDigits = 1)
            )
        )
        assertEquals("1,000,000%", format.format(10000, FormatStyle.PERCENT))
    }

    @Test
    fun `test minimum fraction digits greater than maximum`() {
        val format = NumberFormaterFactory.getInstance(LocaleInfo("en", "US"))
        val invalidSettings = NumberFormaterSettings(
            minimumFractionDigits = 4,
            maximumFractionDigits = 2
        )

        assertEquals("123.46", format.format(123.456, FormatStyle.DECIMAL, invalidSettings))
    }
}
