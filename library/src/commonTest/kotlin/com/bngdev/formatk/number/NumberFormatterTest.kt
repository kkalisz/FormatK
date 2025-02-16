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
        val format = NumberFormatterProvider.getInstance(localeInfo).getFormatter(FormatStyle.DECIMAL)

        val result = format.format(1234.567)

        assertEquals("1,234.567", result)
    }

    @Test
    fun `test formatting decimal value without options in German locale`() {
        val localeInfo = LocaleInfo("de", "DE")
        val format = NumberFormatterProvider.getInstance(localeInfo).getFormatter(FormatStyle.DECIMAL)

        val result = format.format(1234.567)

        assertEquals("1.234,567", result)
    }

    @Test
    fun `test formatting decimal value with custom fraction digits in French locale`() {
        val localeInfo = LocaleInfo("fr", "FR")
        val formatOptions = NumberFormatterSettings(minimumFractionDigits = 2, maximumFractionDigits = 2)
        val format = NumberFormatterProvider.getInstance(localeInfo).getFormatter(FormatStyle.DECIMAL, formatOptions)

        val result = format.format(1234.567)

        assertEquals("1${NNBSP}234,57", result)
    }

    @Test
    fun `test formatting currency value for US locale`() {
        val localeInfo = LocaleInfo("en", "US")
        val format = NumberFormatterProvider.getInstance(localeInfo).getFormatter(FormatStyle.CURRENCY)

        val result = format.format(1234.5)

        assertEquals("$1,234.50", result)
    }

    @Test
    fun `test formatting currency value for Japanese locale`() {
        val localeInfo = LocaleInfo("ja", "JP")
        val format = NumberFormatterProvider.getInstance(
            localeInfo
        ).getFormatter(FormatStyle.CURRENCY, NumberFormatterSettings(currencyCode = "JPY"))

        val result = format.format(1234.51)

        assertEquals("￥1,235", result)
    }

    @Test
    fun `test formatting percentage value for US locale`() {
        val localeInfo = LocaleInfo("en", "US")
        val format = NumberFormatterProvider.getInstance(localeInfo).getFormatter(FormatStyle.PERCENT)

        val result = format.format(0.85)

        assertEquals("85%", result)
    }

    @Test
    fun `test formatting percentage value for German locale`() {
        val localeInfo = LocaleInfo("de", "DE")
        val format = NumberFormatterProvider.getInstance(localeInfo).getFormatter(FormatStyle.PERCENT)

        val result = format.format(0.85)

        assertEquals("85$NBSP%", result)
    }

    @Test
    fun `test applying rounding mode HALF_UP for decimal style`() {
        val localeInfo = LocaleInfo("en", "US")
        val formatOptions = NumberFormatterSettings(
            maximumFractionDigits = 1,
            roundingMode = RoundingMode.HALF_UP
        )
        val format = NumberFormatterProvider.getInstance(localeInfo).getFormatter(FormatStyle.DECIMAL, formatOptions)

        val result = format.format(123.44)

        assertEquals("123.4", result)
    }

    @Test
    fun `test applying rounding mode HALF_DOWN for decimal style`() {
        val localeInfo = LocaleInfo("en", "US")
        val formatOptions = NumberFormatterSettings(
            maximumFractionDigits = 1,
            roundingMode = RoundingMode.HALF_DOWN
        )
        val format = NumberFormatterProvider.getInstance(localeInfo).getFormatter(FormatStyle.DECIMAL, formatOptions)

        val result = format.format(123.45)

        assertEquals("123.4", result)
    }

    @Test
    fun `test currency formatting with custom currency code in US locale`() {
        val localeInfo = LocaleInfo("en", "US")
        val formatOptions = NumberFormatterSettings(currencyCode = "EUR")
        val format = NumberFormatterProvider.getInstance(localeInfo).getFormatter(FormatStyle.CURRENCY, formatOptions)

        val result = format.format(1234.56)

        assertTrue(result.contains("€"))
    }

    @Test
    fun `test currency formatting with custom currency code in German locale`() {
        val localeInfo = LocaleInfo("de", "DE")
        val formatOptions = NumberFormatterSettings(currencyCode = "GBP")
        val format = NumberFormatterProvider.getInstance(localeInfo).getFormatter(FormatStyle.CURRENCY, formatOptions)

        val result = format.format(1234.56)

        assertTrue(result.contains("£"))
    }

    @Test
    fun `test default settings for decimal style in US locale`() {
        val localeInfo = LocaleInfo("en", "US")
        val format = NumberFormatterProvider.getInstance(localeInfo)

        val defaultSettings = format.getDefaultSettings(FormatStyle.DECIMAL)

        assertTrue(defaultSettings.useGrouping!!)
        assertEquals(RoundingMode.HALF_EVEN, defaultSettings.roundingMode)
    }

    @Test
    fun `test default settings for decimal style in Italian locale`() {
        val localeInfo = LocaleInfo("it", "IT")
        val format = NumberFormatterProvider.getInstance(localeInfo)

        val defaultSettings = format.getDefaultSettings(FormatStyle.DECIMAL)

        assertTrue(defaultSettings.useGrouping!!)
        assertEquals(RoundingMode.HALF_EVEN, defaultSettings.roundingMode)
    }

    @Test
    fun `test grouping settings applied`() {
        val localeInfo = LocaleInfo("en", "US")
        val formatOptions = NumberFormatterSettings(useGrouping = false)
        val format = NumberFormatterProvider.getInstance(localeInfo).getFormatter(FormatStyle.DECIMAL, formatOptions)

        val result = format.format(1234567.89)

        assertEquals("1234567.89", result)
    }

    @Test
    fun `test no grouping and custom fraction digits in French locale`() {
        val localeInfo = LocaleInfo("fr", "FR")
        val formatOptions = NumberFormatterSettings(
            useGrouping = false,
            minimumFractionDigits = 3,
            maximumFractionDigits = 3
        )
        val format = NumberFormatterProvider.getInstance(localeInfo).getFormatter(FormatStyle.DECIMAL, formatOptions)

        val result = format.format(1234.567)

        assertEquals("1234,567", result)
    }

    @Test
    fun `test rounding mode CEILING`() {
        val localeInfo = LocaleInfo("en", "US")
        val formatOptions = NumberFormatterSettings(
            maximumFractionDigits = 1,
            roundingMode = RoundingMode.CEILING
        )
        val format = NumberFormatterProvider.getInstance(localeInfo).getFormatter(FormatStyle.DECIMAL, formatOptions)

        val result = format.format(123.41)

        assertEquals("123.5", result)
    }

    @Test
    fun `test rounding mode FLOOR`() {
        val localeInfo = LocaleInfo("en", "US")
        val formatOptions = NumberFormatterSettings(
            maximumFractionDigits = 1,
            roundingMode = RoundingMode.FLOOR
        )
        val format = NumberFormatterProvider.getInstance(localeInfo).getFormatter(FormatStyle.DECIMAL, formatOptions)

        val result = format.format(123.49)

        assertEquals("123.4", result)
    }

    @Test
    fun `test formatting different number types`() {
        val format = NumberFormatterProvider.getInstance(LocaleInfo("en", "US")).getFormatter(FormatStyle.DECIMAL)

        assertEquals("123", format.format(123))
        assertEquals("123.45", format.format(123.45f))
        assertEquals("123.45", format.format(123.45))
        assertEquals("123", format.format(123L))
    }

    @Test
    fun `test formatting with different styles`() {
        val formatFactory = NumberFormatterProvider.getInstance(LocaleInfo("en", "US"))
        val value = 0.456

        assertEquals("0.456", formatFactory.getFormatter(FormatStyle.DECIMAL).format(value))
        assertEquals("$0.46", formatFactory.getFormatter(FormatStyle.CURRENCY).format(value))
        assertEquals("46%", formatFactory.getFormatter(FormatStyle.PERCENT).format(value))
    }

    @Test
    @Ignore
    fun `test formatting with extreme values`() {
        val format = NumberFormatterProvider.getInstance(LocaleInfo("en", "US")).getFormatter(FormatStyle.DECIMAL)

        // assertEquals("1.23E8", format.format(123456789.0, FormatStyle.DECIMAL))
        assertEquals("0", format.format(0.000000001))
        assertEquals("∞", format.format(Double.POSITIVE_INFINITY))
        assertEquals("-∞", format.format(Double.NEGATIVE_INFINITY))
        assertEquals("NaN", format.format(Double.NaN))
    }

    @Test
    fun `test formatting with grouping`() {
        val withoutGrouping = NumberFormatterSettings(useGrouping = false)
        val format = NumberFormatterProvider.getInstance(
            LocaleInfo("en", "US")
        ).getFormatter(FormatStyle.DECIMAL, withoutGrouping)

        assertEquals("1234567.89", format.format(1234567.89))
    }

    @Test
    fun `test formatting without grouping`() {
        val withoutGrouping = NumberFormatterSettings(useGrouping = false)
        val format = NumberFormatterProvider.getInstance(
            LocaleInfo("en", "US")
        ).getFormatter(FormatStyle.DECIMAL, withoutGrouping)

        assertEquals("1234567.89", format.format(1234567.89))
    }

    @Test
    fun `test formatting with different rounding modes`() {
        val formatFactory = NumberFormatterProvider.getInstance(LocaleInfo("en", "US"))
        val value = 123.456
        val options = NumberFormatterSettings(
            maximumFractionDigits = 2,
            minimumFractionDigits = 2
        )

        val roundUp = options.copy(roundingMode = RoundingMode.UP)
        val roundDown = options.copy(roundingMode = RoundingMode.DOWN)
        val roundHalfUp = options.copy(roundingMode = RoundingMode.HALF_UP)

        assertEquals("123.46", formatFactory.getFormatter(FormatStyle.DECIMAL, roundUp).format(value))
        assertEquals("123.45", formatFactory.getFormatter(FormatStyle.DECIMAL, roundDown).format(value))
        assertEquals("123.46", formatFactory.getFormatter(FormatStyle.DECIMAL, roundHalfUp).format(value))
    }

    @Test
    @Ignore
    fun `test formatting with different currency codes`() {
        val formatFactory = NumberFormatterProvider.getInstance(LocaleInfo("en", "US"))
        val value = 123.45

        val usdOptions = NumberFormatterSettings(currencyCode = "USD")
        val eurOptions = NumberFormatterSettings(currencyCode = "EUR")
        val jpyOptions = NumberFormatterSettings(currencyCode = "JPY")

        formatFactory.getFormatter(FormatStyle.CURRENCY, usdOptions).format(value)

        assertEquals("$123.45", formatFactory.getFormatter(FormatStyle.CURRENCY, usdOptions).format(value))
        assertEquals("€123.45", formatFactory.getFormatter(FormatStyle.CURRENCY, eurOptions).format(value))
        assertEquals("￥123.45", formatFactory.getFormatter(FormatStyle.CURRENCY, jpyOptions).format(value))
    }

    @Test
    fun `test default settings for different styles`() {
        val format = NumberFormatterProvider.getInstance(LocaleInfo("en", "US"))

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
        val formatFactory = NumberFormatterProvider.getInstance(LocaleInfo("en", "US"))
        val value = 123.45

        assertEquals(
            formatFactory.getFormatter(FormatStyle.DECIMAL).format(value),
            formatFactory.getFormatter().format(value)
        )
    }

    @Ignore
    @Test
    fun `test formatting with invalid currency code`() {
        val options = NumberFormatterSettings(currencyCode = "INVALID")
        val format = NumberFormatterProvider.getInstance(
            LocaleInfo("en", "US")
        ).getFormatter(FormatStyle.CURRENCY, options)

        assertFailsWith<IllegalArgumentException> {
            format.format(123.45)
        }
    }

    @Test
    fun `test formatting negative numbers with different styles`() {
        val formatFactory = NumberFormatterProvider.getInstance(LocaleInfo("en", "US"))
        val value = -123.45

        assertEquals("-123.45", formatFactory.getFormatter(FormatStyle.DECIMAL).format(value))
        assertEquals("-$123.45", formatFactory.getFormatter(FormatStyle.CURRENCY).format(value))
        assertEquals("-12,345%", formatFactory.getFormatter(FormatStyle.PERCENT).format(value))
    }

    @Test
    fun `test formatting with zero values`() {
        val formatFactory = NumberFormatterProvider.getInstance(LocaleInfo("en", "US"))

        assertEquals("0", formatFactory.getFormatter(FormatStyle.DECIMAL).format(0))
        assertEquals("$0.00", formatFactory.getFormatter(FormatStyle.CURRENCY).format(0.0))
        assertEquals("0%", formatFactory.getFormatter(FormatStyle.PERCENT).format(0))
    }

    @Test
    fun `test formatting with exact rounding boundaries`() {
        val formatFactory = NumberFormatterProvider.getInstance(LocaleInfo("en", "US"))
        val options = NumberFormatterSettings(
            maximumFractionDigits = 1,
            minimumFractionDigits = 1
        )

        assertEquals(
            "1.5",
            formatFactory.getFormatter(
                FormatStyle.DECIMAL,
                options.copy(roundingMode = RoundingMode.HALF_UP)
            ).format(1.45)
        )
        assertEquals(
            "1.4",
            formatFactory.getFormatter(
                FormatStyle.DECIMAL,
                options.copy(roundingMode = RoundingMode.HALF_DOWN)
            ).format(1.45)
        )
        assertEquals(
            "1.4",
            formatFactory.getFormatter(FormatStyle.DECIMAL, options.copy(roundingMode = RoundingMode.DOWN)).format(1.45)
        )
        assertEquals(
            "1.5",
            formatFactory.getFormatter(FormatStyle.DECIMAL, options.copy(roundingMode = RoundingMode.UP)).format(1.45)
        )
    }

    @Test
    fun `test formatting with various fraction digit settings`() {
        val formatFactory = NumberFormatterProvider.getInstance(LocaleInfo("en", "US"))
        val value = 123.456789

        val noFractionFormat = formatFactory.getFormatter(
            FormatStyle.DECIMAL,
            NumberFormatterSettings(
                minimumFractionDigits = 0,
                maximumFractionDigits = 0
            )
        )
        val exactFractionFormat = formatFactory.getFormatter(
            FormatStyle.DECIMAL,
            NumberFormatterSettings(
                minimumFractionDigits = 3,
                maximumFractionDigits = 3
            )
        )
        val rangeFractionFormat = formatFactory.getFormatter(
            FormatStyle.DECIMAL,
            NumberFormatterSettings(
                minimumFractionDigits = 2,
                maximumFractionDigits = 4
            )
        )

        assertEquals("123", noFractionFormat.format(value))
        assertEquals("123.457", exactFractionFormat.format(value))
        assertEquals("123.4568", rangeFractionFormat.format(value))
    }

    @Test
    fun `test formatting with different locales and separators`() {
        val value = 1234567.89

        val usFormat = NumberFormatterProvider.getInstance(LocaleInfo("en", "US")).getFormatter()
        val deFormat = NumberFormatterProvider.getInstance(LocaleInfo("de", "DE")).getFormatter()
        val frFormat = NumberFormatterProvider.getInstance(LocaleInfo("fr", "FR")).getFormatter()
        val itFormat = NumberFormatterProvider.getInstance(LocaleInfo("it", "IT")).getFormatter()

        assertEquals("1,234,567.89", usFormat.format(value))
        assertEquals("1.234.567,89", deFormat.format(value))
        assertEquals("1${NNBSP}234${NNBSP}567,89", frFormat.format(value))
        assertEquals("1.234.567,89", itFormat.format(value))
    }

    @Test
    fun `test formatting with combination of all settings`() {
        val formatFactory = NumberFormatterProvider.getInstance(LocaleInfo("en", "US"))
        val value = 12345.6789

        val complexSettings = NumberFormatterSettings(
            useGrouping = true,
            roundingMode = RoundingMode.HALF_UP,
            minimumFractionDigits = 2,
            maximumFractionDigits = 3,
            currencyCode = "EUR"
        )

        assertEquals("12,345.679", formatFactory.getFormatter(FormatStyle.DECIMAL, complexSettings).format(value))
        assertEquals("€12,345.679", formatFactory.getFormatter(FormatStyle.CURRENCY, complexSettings).format(value))
        assertEquals("1,234,567.89%", formatFactory.getFormatter(FormatStyle.PERCENT, complexSettings).format(value))
    }

    @Test
    fun `test formatting edge numbers`() {
        val format = NumberFormatterProvider.getFormater(LocaleInfo("en", "US"), FormatStyle.DECIMAL)

        assertEquals("0.001", format.format(0.001))
        assertEquals("1,000,000", format.format(1000000))

        val formatPercent =
            NumberFormatterProvider.getFormater(
                LocaleInfo("en", "US"),
                FormatStyle.PERCENT,
                NumberFormatterSettings(minimumFractionDigits = 1, maximumFractionDigits = 1)
            )

        assertEquals(
            "0.1%",
            formatPercent.format(
                0.001
            )
        )
        val formatPercentDefault = NumberFormatterProvider.getFormater(LocaleInfo("en", "US"), FormatStyle.PERCENT)

        assertEquals("1,000,000%", formatPercentDefault.format(10000))
    }

    @Test
    fun `test minimum fraction digits greater than maximum`() {
        val invalidSettings = NumberFormatterSettings(
            minimumFractionDigits = 4,
            maximumFractionDigits = 2
        )
        val format = NumberFormatterProvider.getInstance(
            LocaleInfo("en", "US")
        ).getFormatter(FormatStyle.DECIMAL, invalidSettings)

        assertEquals("123.46", format.format(123.456))
    }
}
