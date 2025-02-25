package com.bngdev.formatk.number

import com.bngdev.formatk.LocaleInfo
import com.bngdev.formatk.date.DateFormatterFactory
import com.bngdev.formatk.date.DateFormatterProvider
import com.bngdev.formatk.date.DateFormatterSettings
import com.bngdev.formatk.date.getFormatter
import kotlinx.datetime.Instant
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotEquals
import kotlin.test.expect

class DateFormatterTest {

    @Test
    fun `test basic locale creation`() {
        val locale = LocaleInfo("en", "US")
        val formatter = DateFormatterProvider.getFormatter(locale)
        val date = Instant.parse("2017-12-04T08:07:00Z")
        println(date.toString())
        assertEquals("21312", formatter.format(date))
    }
}
