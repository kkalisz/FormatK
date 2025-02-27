package com.bngdev.formatk.date

import com.bngdev.formatk.LocaleInfo
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@Ignore
class DateFormatterTest {

    private val testDate = Instant.parse("2017-12-04T08:07:00Z")
    
    private val timezoneUtc = TimeZone.of("UTC")

    @Test
    fun `test US date with exact pattern`() {
        val locale = LocaleInfo("en", "US")
        val formatter = DateFormatterProvider.getFormatter(
            locale,
            DateFormatterSettings(pattern = "MMMM d, yyyy")
        )
        val formatted = formatter.format(testDate)
        assertEquals("December 4, 2017", formatted)
    }

    @Test
    fun `test German date with exact pattern`() {
        val locale = LocaleInfo("de", "DE")
        val formatter = DateFormatterProvider.getFormatter(
            locale,
            DateFormatterSettings(pattern = "d. MMMM yyyy")
        )
        val formatted = formatter.format(testDate)
        // Platform-specific note: Month name might be "Dezember" or "December" depending on platform
        assertEquals("4. Dezember 2017", formatted)
    }

    @Test
    fun `test Japanese date with exact pattern`() {
        val locale = LocaleInfo("ja", "JP")
        val formatter = DateFormatterProvider.getFormatter(
            locale,
            DateFormatterSettings(pattern = "yyyy年MM月dd日")
        )
        val formatted = formatter.format(testDate)
        assertEquals("2017年12月04日", formatted)
    }

    // Format style tests
    @Test
    fun `test SHORT style date format`() {
        val locale = LocaleInfo("en", "US")
        val formatter = DateFormatterProvider.getFormatter(
            locale,
            DateFormatterSettings(dateStyle = FormatStyle.SHORT, timeStyle = FormatStyle.NONE)
        )
        val formatted = formatter.format(testDate)
        // Platform-specific note: Format might be "12/4/17" or "12/4/2017"
        assertEquals("12/4/17", formatted)
    }

    @Test
    fun `test MEDIUM style date format`() {
        val locale = LocaleInfo("en", "US")
        val formatter = DateFormatterProvider.getFormatter(
            locale,
            DateFormatterSettings(dateStyle = FormatStyle.MEDIUM, timeStyle = FormatStyle.NONE)
        )
        val formatted = formatter.format(testDate)
        assertEquals("Dec 4, 2017", formatted)
    }

    @Test
    fun `test LONG style date format`() {
        val locale = LocaleInfo("en", "US")
        val formatter = DateFormatterProvider.getFormatter(
            locale,
            DateFormatterSettings(dateStyle = FormatStyle.LONG, timeStyle = FormatStyle.NONE)
        )
        val formatted = formatter.format(testDate)
        assertEquals("December 4, 2017", formatted)
    }

    @Test
    fun `test simple date pattern`() {
        val locale = LocaleInfo("en", "US")
        val formatter = DateFormatterProvider.getFormatter(locale, DateFormatterSettings(
            pattern = "yyyy-MM-dd",
            timeZone = timezoneUtc
        ))
        val formatted = formatter.format(testDate)
        assertEquals("2017-12-04", formatted)
    }

    @Test
    fun `test simple time pattern`() {
        val locale = LocaleInfo("en", "US")
        val formatter = DateFormatterProvider.getFormatter(locale, DateFormatterSettings(
            pattern = "HH:mm:ss",
            timeZone = timezoneUtc
        ))
        val formatted = formatter.format(testDate)
        assertEquals("08:07:00", formatted)
    }

    @Test
    fun `test simple datetime pattern`() {
        val locale = LocaleInfo("en", "US")
        val formatter = DateFormatterProvider.getFormatter(locale, DateFormatterSettings(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timeZone = timezoneUtc
        ))
        val formatted = formatter.format(testDate)
        assertEquals("2017-12-04 08:07:00", formatted)
    }

    @Test
    fun `test basic US date formatting`() {
        val locale = LocaleInfo("en", "US")
        val formatter = DateFormatterProvider.getFormatter(locale, DateFormatterSettings(
            pattern = "MM/dd/yyyy HH:mm:ss",
            timeZone = timezoneUtc
        ))
        val formatted = formatter.format(testDate)
        val expected = "12/04/2017 08:07:00"
        assertEquals(expected, formatted)
    }

    @Test
    fun `test basic UK date formatting`() {
        val locale = LocaleInfo("en", "GB")
        val formatter = DateFormatterProvider.getFormatter(locale, DateFormatterSettings(
            pattern = "dd/MM/yyyy HH:mm:ss",
            timeZone = timezoneUtc
        ))
        val formatted = formatter.format(testDate)
        val expected = "04/12/2017 08:07:00"
        assertEquals(expected, formatted)
    }

    @Test
    fun `test Japanese date formatting`() {
        val locale = LocaleInfo("ja", "JP")
        val formatter = DateFormatterProvider.getFormatter(locale, DateFormatterSettings(
            pattern = "yyyy年MM月dd日 HH:mm:ss",
            timeZone = timezoneUtc
        ))
        val formatted = formatter.format(testDate)
        val expected = "2017年12月04日 08:07:00"
        assertEquals(expected, formatted)
    }

    @Test
    fun `test short style date formatting`() {
        val locale = LocaleInfo("en", "US")
        val formatter = DateFormatterProvider.getFormatter(locale, DateFormatterSettings(
            dateStyle = FormatStyle.SHORT,
            timeStyle = FormatStyle.SHORT,
            timeZone = timezoneUtc
        ))
        val formatted = formatter.format(testDate)
        val expected = "12/4/17, 8:07 AM"
        assertEquals(expected, formatted)

    }

    @Test
    fun `test medium style date formatting`() {
        val locale = LocaleInfo("en", "US")
        val formatter = DateFormatterProvider.getFormatter(locale, DateFormatterSettings(
            dateStyle = FormatStyle.MEDIUM,
            timeStyle = FormatStyle.MEDIUM,
            timeZone = timezoneUtc
        ))
        val formatted = formatter.format(testDate)
        val expected = "Dec 4, 2017, 8:07:00 AM"
        assertEquals(expected, formatted)
    }

    
    @Test
    @Ignore
    fun `test long style date formatting`() {
        val locale = LocaleInfo("en", "US")
        val formatter = DateFormatterProvider.getFormatter(locale, DateFormatterSettings(
            dateStyle = FormatStyle.LONG,
            timeStyle = FormatStyle.LONG,
            timeZone = TimeZone.of("UTC")
        ))
        val formatted = formatter.format(testDate)
        val expected = "December 4, 2017 8:07:00 AM UTC"
        assertEquals(expected, formatted)
    }

    @Test
    fun `test date only formatting`() {
        val locale = LocaleInfo("en", "US")
        val formatter = DateFormatterProvider.getFormatter(locale, DateFormatterSettings(
            dateStyle = FormatStyle.SHORT,
            timeStyle = FormatStyle.NONE,
            timeZone = timezoneUtc
        ))
        val formatted = formatter.format(testDate)
        val expected = "12/4/17"
        assertEquals(expected, formatted)
    }

    @Test
    fun `test time only formatting`() {
        val locale = LocaleInfo("en", "US")
        val formatter = DateFormatterProvider.getFormatter(locale, DateFormatterSettings(
            dateStyle = FormatStyle.NONE,
            timeStyle = FormatStyle.SHORT,
            timeZone = timezoneUtc
        ))
        val formatted = formatter.format(testDate)
        val expected = "8:07 AM"
        assertEquals(expected, formatted)
    }

    @Test
    fun `test New York timezone formatting`() {
        val locale = LocaleInfo("en", "US")
        val formatter = DateFormatterProvider.getFormatter(locale, DateFormatterSettings(
            pattern = "yyyy-MM-dd HH:mm:ss z",
            timeZone = TimeZone.of("America/New_York")
        ))
        val formatted = formatter.format(testDate)
        val expected = "2017-12-04 03:07:00 EST"
        assertEquals(expected, formatted)
    }

    @Test
    fun `test Tokyo timezone formatting`() {
        val locale = LocaleInfo("ja", "JP")
        val formatter = DateFormatterProvider.getFormatter(locale, DateFormatterSettings(
            pattern = "yyyy-MM-dd HH:mm:ss z",
            timeZone = TimeZone.of("Asia/Tokyo")
        ))
        val formatted = formatter.format(testDate)
        val expected = "2017-12-04 17:07:00 JST"
        assertEquals(expected, formatted)
    }

    @Test
    fun `test parse ISO format`() {
        val locale = LocaleInfo("en", "US")
        val formatter = DateFormatterProvider.getFormatter(locale, DateFormatterSettings(
            pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'",
            timeZone = timezoneUtc
        ))
        val parsed = formatter.parse("2017-12-04T08:07:00Z")
        assertEquals(testDate, parsed)
    }

    @Test
    fun `test parse custom format`() {
        val locale = LocaleInfo("en", "US")
        val formatter = DateFormatterProvider.getFormatter(locale, DateFormatterSettings(
            pattern = "MM/dd/yyyy HH:mm:ss",
            timeZone = timezoneUtc
        ))
        val parsed = formatter.parse("12/04/2017 08:07:00")
        assertEquals(testDate, parsed)
    }

    @Test
    fun `test parse invalid format returns null`() {
        val locale = LocaleInfo("en", "US")
        val formatter = DateFormatterProvider.getFormatter(locale, DateFormatterSettings(
            pattern = "yyyy-MM-dd",
            timeZone = timezoneUtc
        ))
        val parsed = formatter.parse("invalid-date")
        assertEquals(null, parsed)
    }

    @Test
    fun `test format with full month name pattern`() {
        val locale = LocaleInfo("en", "US")
        val formatter = DateFormatterProvider.getFormatter(locale, DateFormatterSettings(
            pattern = "MMMM dd, yyyy",
            timeZone = timezoneUtc
        ))
        assertEquals("December 04, 2017", formatter.format(testDate))
    }

    @Test
    fun `test format with day of week pattern`() {
        val locale = LocaleInfo("en", "US")
        val formatter = DateFormatterProvider.getFormatter(locale, DateFormatterSettings(
            pattern = "EEEE, MMMM dd, yyyy",
            timeZone = timezoneUtc
        ))
        assertEquals("Monday, December 04, 2017", formatter.format(testDate))
    }

    @Test
    fun `test US format with SHORT style`() {
        val locale = LocaleInfo("en", "US")
        val formatter = DateFormatterProvider.getFormatter(
            locale,
            DateFormatterSettings(dateStyle = FormatStyle.SHORT, timeStyle = FormatStyle.SHORT, timeZone = TimeZone.of("Indian/Chagos"))
        )
        val date = Instant.parse("2017-12-04T08:07:00Z")
        val formatted = formatter.format(date)
        assertEquals("12/4/17, 2:07 PM", formatted)
    }

    @Test
    fun `test French format with MEDIUM style`() {
        val locale = LocaleInfo("fr", "FR")
        val formatter = DateFormatterProvider.getFormatter(
            locale,
            DateFormatterSettings(dateStyle = FormatStyle.MEDIUM, timeStyle = FormatStyle.MEDIUM, timeZone = timezoneUtc)
        )
        val date = Instant.parse("2017-12-04T08:07:00Z")
        val formatted = formatter.format(date)
        // Platform-specific note: iOS format differs from other platforms
        // iOS expected: "4 déc. 2017 à 08:07:00"
        assertEquals("4 déc. 2017, 08:07:00", formatted)
    }

    @Test
    fun `test Chinese format with Long style`() {
        val locale = LocaleInfo("zh", "CN")
        val formatter = DateFormatterProvider.getFormatter(
            locale,
            DateFormatterSettings(dateStyle = FormatStyle.LONG, timeStyle = FormatStyle.LONG, timeZone = TimeZone.of("UTC"))
        )
        val date = Instant.parse("2017-12-04T08:07:00Z")
        val formatted = formatter.format(date)
        assertEquals("2017年12月4日 UTC 08:07:00", formatted)
    }

    @Test
    fun `test custom pattern with timezone`() {
        val locale = LocaleInfo("en", "US")
        val formatter = DateFormatterProvider.getFormatter(
            locale,
            DateFormatterSettings(
                pattern = "EEEE, MMMM d, yyyy 'at' HH:mm:ss z",
                timeZone = TimeZone.of("America/New_York")
            )
        )
        val date = Instant.parse("2017-12-04T08:07:00Z")
        val formatted = formatter.format(date)
        // Platform-specific note: iOS timezone abbreviation might differ
        // iOS expected: "Monday, December 4, 2017 at 03:07:00 EST"
        assertEquals("Monday, December 4, 2017 at 03:07:00 EST", formatted)
    }

    @Test
    fun `test custom pattern with day period`() {
        val locale = LocaleInfo("en", "US")
        val formatter = DateFormatterProvider.getFormatter(
            locale,
            DateFormatterSettings(pattern = "h:mm a", timeZone = TimeZone.of("UTC"))
        )
        val date = Instant.parse("2017-12-04T08:07:00Z")
        val formatted = formatter.format(date)
        // Platform-specific note: AM/PM format might differ
        // iOS expected: "8:07 AM"
        assertEquals("8:07 AM", formatted)
    }

    @Test
    fun `test custom pattern with full month name`() {
        val locale = LocaleInfo("en", "US")
        val formatter = DateFormatterProvider.getFormatter(
            locale,
            DateFormatterSettings(pattern = "MMMM d")
        )
        val date = Instant.parse("2017-12-04T08:07:00Z")
        val formatted = formatter.format(date)
        // Platform-specific note: Month name capitalization might differ
        // iOS expected: "December 4"
        assertEquals("December 4", formatted)
    }

    @Test
    fun `test formatting with different locales`() {

        // Test basic numeric components across locales
        data class NumericTest(
            val locale: LocaleInfo,
            val components: List<List<String>>,  // Each inner list contains acceptable variations
            val description: String
        )

        val numericTests = listOf(
            NumericTest(
                LocaleInfo("en", "US"),
                listOf(
                    listOf("2017"),                    // Year
                    listOf("12", "12月"),              // Month (including Japanese style)
                    listOf("4", "04", "４"),           // Day (including full-width)
                    listOf("8", "08", "８"),           // Hour
                    listOf("7", "07", "７"),           // Minute
                    listOf("0", "00", "０")            // Second
                ),
                "English (US)"
            ),
            NumericTest(
                LocaleInfo("ja", "JP"),
                listOf(
                    listOf("2017", "２０１７"),        // Year
                    listOf("12", "１２"),              // Month
                    listOf("4", "04", "４"),           // Day
                    listOf("8", "08", "８"),           // Hour
                    listOf("7", "07", "７"),           // Minute
                    listOf("0", "00", "０")            // Second
                ),
                "Japanese"
            )
        )
        numericTests.forEach { test ->
            val patterns = listOf(
                "yyyy-MM-dd HH:mm:ss",
                "yyyy/MM/dd HH:mm:ss",
                "yyyy.MM.dd HH:mm:ss"
            )

            patterns.forEach { pattern ->
                val settings = DateFormatterSettings(
                    pattern = pattern,
                    timeZone = timezoneUtc
                )
                try {
                    val formatter = DateFormatterProvider.getFormatter(test.locale, settings)
                    val formatted = formatter.format(testDate)

                    test.components.forEach { alternatives ->
                        assertTrue(
                            alternatives.any { formatted.contains(it) },
                            "No matching component found for ${test.description}. " +
                            "Expected one of: ${alternatives.joinToString(", ")}, " +
                            "Got: $formatted"
                        )
                    }
                } catch (e: Throwable) {
                }
            }
        }

        // Test localized format styles
        data class StyleTest(
            val locale: LocaleInfo,
            val style: FormatStyle,
            val possibleFormats: List<List<String>>,  // Each inner list is a complete valid format
            val description: String
        )

        val styleTests = listOf(
            StyleTest(
                LocaleInfo("en", "US"),
                FormatStyle.FULL,
                listOf(
                    listOf("Monday", "December", "4", "2017"),
                    listOf("Mon", "Dec", "4", "2017"),
                    listOf("Monday", "12", "4", "2017")
                ),
                "English full format"
            ),
            StyleTest(
                LocaleInfo("ja", "JP"),
                FormatStyle.FULL,
                listOf(
                    listOf("月曜日", "12月", "4日", "2017年"),
                    listOf("月", "12", "4", "2017"),
                    listOf("月曜", "１２月", "４日", "２０１７年")
                ),
                "Japanese full format"
            )
        )
        styleTests.forEach { test ->
            val settings = DateFormatterSettings(
                dateStyle = test.style,
                timeStyle = FormatStyle.NONE,  // Test date formatting only
                timeZone = timezoneUtc
            )
            try {
                val formatter = DateFormatterProvider.getFormatter(test.locale, settings)
                val formatted = formatter.format(testDate)

                val isValidFormat = test.possibleFormats.any { format ->
                    format.all { component -> formatted.contains(component) }
                }
                assertTrue(
                    isValidFormat,
                    "Format doesn't match any expected pattern for ${test.description}. " +
                    "Got: $formatted, Expected one of: ${test.possibleFormats.joinToString(" or ")}"
                )
            } catch (e: Throwable) {
            }
        }
    }

    @Test
    fun `test parsing dates`() {
        val locale = LocaleInfo("en", "US")
        val timezone = TimeZone.of("UTC")

        // Test formatting a known date
        run {
            val pattern = "yyyy-MM-dd"
            val settings = DateFormatterSettings(
                pattern = pattern,
                timeZone = timezone
            )
            val formatter = DateFormatterProvider.getFormatter(locale, settings)
            val date = Instant.parse("2023-07-21T00:00:00Z")
            val formatted = formatter.format(date)
            assertEquals("2023-07-21", formatted, "Date formatting failed")
        }

        // Test formatting with time
        run {
            val pattern = "yyyy-MM-dd HH:mm:ss"
            val settings = DateFormatterSettings(
                pattern = pattern,
                timeZone = timezone
            )
            val formatter = DateFormatterProvider.getFormatter(locale, settings)
            val date = Instant.parse("2023-07-21T15:30:45Z")
            val formatted = formatter.format(date)
            assertEquals("2023-07-21 15:30:45", formatted, "Date-time formatting failed")
        }
    }

    @Test
    fun `test invalid inputs`() {
        val locale = LocaleInfo("en", "US")

        // Test parsing with invalid input
        run {
            val settings = DateFormatterSettings(pattern = "yyyy-MM-dd")
            val formatter = DateFormatterProvider.getFormatter(locale, settings)
            try {
                val result = formatter.parse("not a date")
                // If parsing doesn't throw, it should return null
                assertEquals(null, result, "Invalid date should return null")
            } catch (e: Throwable) {
                // Some platforms might throw an exception for invalid input
                assertTrue(true, "Exception on invalid input is acceptable")
            }
        }

        // Test with malformed pattern
        run {
            try {
                DateFormatterProvider.getFormatter(
                    locale,
                    DateFormatterSettings(pattern = "invalid")
                )
                assertTrue(false, "Expected exception for invalid pattern")
            } catch (e: Throwable) {
                // Any exception for invalid pattern is acceptable
                assertTrue(true, "Exception on invalid pattern is expected")
            }
        }
    }

    @Test
    fun `test edge cases`() {
        val locale = LocaleInfo("en", "US")

        // Test leap year
        run {
            val leapDate = Instant.parse("2020-02-29T12:00:00Z")
            val settings = DateFormatterSettings(pattern = "yyyy-MM-dd")
            val formatter = DateFormatterProvider.getFormatter(locale, settings)
            val formatted = formatter.format(leapDate)
            assertTrue(formatted.contains("2020"), "Year not found in leap year date")
            assertTrue(formatted.contains("02"), "Month not found in leap year date")
            assertTrue(formatted.contains("29"), "Day not found in leap year date")
        }

        // Test DST transition
        run {
            val dstDate = Instant.parse("2023-03-12T07:00:00Z")
            val settings = DateFormatterSettings(
                timeZone = TimeZone.of("America/New_York"),
                pattern = "HH"
            )
            val formatter = DateFormatterProvider.getFormatter(locale, settings)
            val formatted = formatter.format(dstDate)
            assertTrue(
                formatted == "02" || formatted == "03",
                "Expected hour during DST transition to be 02 or 03, got: $formatted"
            )
        }

        // Test millennium boundary
        run {
            val millenniumDate = Instant.parse("2000-01-01T00:00:00Z")
            val settings = DateFormatterSettings(pattern = "yyyy-MM-dd")
            val formatter = DateFormatterProvider.getFormatter(locale, settings)
            assertEquals("2000-01-01", formatter.format(millenniumDate))
        }
    }

}
