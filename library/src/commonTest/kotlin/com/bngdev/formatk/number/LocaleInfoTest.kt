package com.bngdev.formatk.number

import com.bngdev.formatk.LocaleInfo
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotEquals

class LocaleInfoTest {

    @Test
    fun `test basic locale creation`() {
        val locale = LocaleInfo("en", "US")
        assertEquals("en", locale.language)
        assertEquals("US", locale.region)
    }

    @Test
    fun `test toLanguageTag without validation`() {
        val testCases = listOf(
            Triple("en", "US", "en-US"),
            Triple("es", "MX", "es-MX"),
            Triple("eng", "GB", "eng-GB"),
            Triple(" en ", " US ", "en-US"), // Testing trim
            Triple("EN", "us", "en-US") // Testing case conversion
        )

        testCases.forEach { (language, region, expected) ->
            val locale = LocaleInfo(language, region)
            assertEquals(expected, locale.toLanguageTag())
        }
    }

    @Test
    fun `test toLanguageTag with validation - valid cases`() {
        val testCases = listOf(
            Triple("en", "US", "en-US"),
            Triple("es", "MX", "es-MX"),
            Triple("eng", "GB", "eng-GB")
        )

        testCases.forEach { (language, region, expected) ->
            val locale = LocaleInfo(language, region)
            assertEquals(expected, locale.toLanguageTag(validate = true))
        }
    }

    @Test
    fun `test toLanguageTag with validation - invalid language`() {
        val invalidLanguages = listOf(
            "e", // too short
            "engl", // too long
            "e1", // contains number
            "en-", // contains special character
            "" // empty
        )

        invalidLanguages.forEach { language ->
            val locale = LocaleInfo(language, "US")
            assertFailsWith<IllegalArgumentException>(
                message = "Should fail for invalid language: $language"
            ) {
                locale.toLanguageTag(validate = true)
            }
        }
    }

    @Test
    fun `test toLanguageTag with validation - invalid region`() {
        val invalidRegions = listOf(
            "U", // too short
            "USA", // too long
            "U1", // contains number
            "U-", // contains special character
            "" // empty
        )

        invalidRegions.forEach { region ->
            val locale = LocaleInfo("en", region)
            assertFailsWith<IllegalArgumentException>(
                message = "Should fail for invalid region: $region"
            ) {
                locale.toLanguageTag(validate = true)
            }
        }
    }

    @Test
    fun `test data class equality`() {
        val locale1 = LocaleInfo("en", "US")
        val locale2 = LocaleInfo("en", "US")
        val locale3 = LocaleInfo("fr", "FR")

        assertEquals(locale1, locale2)
        assertNotEquals(locale1, locale3)
    }

    @Test
    fun `test data class copy`() {
        val original = LocaleInfo("en", "US")
        val copied = original.copy(language = "fr")

        assertEquals("fr", copied.language)
        assertEquals("US", copied.region)
        assertNotEquals(original, copied)
    }
}
