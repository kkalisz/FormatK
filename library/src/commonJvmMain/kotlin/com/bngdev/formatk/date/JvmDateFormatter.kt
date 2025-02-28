package com.bngdev.formatk.date

import com.bngdev.formatk.utils.normalizeWhitespaces
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toKotlinInstant
import java.text.ParseException
import java.time.format.DateTimeFormatter

class JvmDateFormatter(private val dateTimeFormatter: DateTimeFormatter) : DateFormatter {

    override fun format(instant: Instant): String {
        return dateTimeFormatter.format(instant.toJavaInstant()).normalizeWhitespaces()
    }

    override fun parse(value: String): Instant? {
        return try {
            val date = dateTimeFormatter.parse(value, java.time.Instant::from)
            return date.toKotlinInstant()

        } catch (e: ParseException) {
            null
        }
    }
}
