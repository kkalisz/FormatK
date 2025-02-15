package com.bngdev.formatk.number

import com.bngdev.formatk.CurrencyInfo
import com.bngdev.formatk.LocaleInfo
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class CurrencyInfoTest {

    @Test
    fun `test getCurrency with valid underscore locale tag`() {
        val currency = CurrencyInfo.getCurrency("en_US")
        assertEquals("USD", currency?.code)
        assertEquals("$", currency?.symbol)
    }

    @Test
    fun `test getCurrency with valid hyphen locale tag`() {
        val currency = CurrencyInfo.getCurrency("en-GB")
        assertEquals("GBP", currency?.code)
        assertEquals("£", currency?.symbol)
    }

    @Test
    fun `test getCurrency with direct country code`() {
        val currency = CurrencyInfo.getCurrency("JP")
        assertEquals("JPY", currency?.code)
        assertEquals("￥", currency?.symbol)
    }

    @Test
    fun `test getCurrency with invalid locale returns null`() {
        val currency = CurrencyInfo.getCurrency("invalid_locale")
        assertNull(currency)
    }

    @Test
    fun `test getCurrency with LocaleInfo`() {
        val localeInfo = LocaleInfo("en", "US")
        val currency = CurrencyInfo.getCurrency(localeInfo)
        assertEquals("USD", currency?.code)
    }

    @Test
    fun `test getLocalesByCurrencyCode with EUR returns all Euro countries`() {
        val locales = CurrencyInfo.getLocalesByCurrencyCode("EUR")
        assertContains(locales, "AD")
        assertEquals(23, locales.size)
    }

    @Test
    fun `test getLocalesByCurrencyCode with XCD returns multiple Caribbean countries`() {
        val locales = CurrencyInfo.getLocalesByCurrencyCode("XCD")
        assertContains(locales, "AG")
        assertContains(locales, "AI")
        assertContains(locales, "KN")
        assertContains(locales, "MS")
        assertEquals(4, locales.size)
    }

    @Test
    fun `test getLocalesByCurrencyCode with AED returns single country`() {
        val locales = CurrencyInfo.getLocalesByCurrencyCode("AED")
        assertEquals(listOf("AE"), locales)
        assertEquals(1, locales.size)
    }

    @Test
    fun `test getLocalesByCurrencyCode is case insensitive`() {
        val upperCaseResult = CurrencyInfo.getLocalesByCurrencyCode("EUR")
        val lowerCaseResult = CurrencyInfo.getLocalesByCurrencyCode("eur")
        assertEquals(upperCaseResult, lowerCaseResult)
    }

    @Test
    fun `test getLocalesByCurrencyCode with non-existent currency returns empty list`() {
        val locales = CurrencyInfo.getLocalesByCurrencyCode("XXX")
        assertTrue(locales.isEmpty())
    }

    @Test
    fun `test getCurrencyByCode with valid code`() {
        val currency = CurrencyInfo.getCurrencyByCode("EUR")
        assertEquals("EUR", currency?.code)
        assertEquals("€", currency?.symbol)
    }

    @Test
    fun `test getCurrencyByCode with invalid code returns null`() {
        val currency = CurrencyInfo.getCurrencyByCode("XXX")
        assertNull(currency)
    }

    @Test
    fun `test getCurrencyByCode is case insensitive`() {
        val currency1 = CurrencyInfo.getCurrencyByCode("usd")
        val currency2 = CurrencyInfo.getCurrencyByCode("USD")
        assertEquals(currency1, currency2)
    }

    @Test
    fun `test currency properties`() {
        val currency = CurrencyInfo.getCurrency("US")
        assertEquals("United States dollar", currency?.name)
        assertEquals("USD", currency?.code)
        assertEquals("$", currency?.symbol)
        assertEquals(2, currency?.fractionDigits)
    }
}
