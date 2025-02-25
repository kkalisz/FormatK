package com.bngdev.formatk.date

import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toKotlinInstant
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

class LegacyJvmDateFormatter(private val simpleDateFormat: SimpleDateFormat) : DateFormatter {

    override fun format(instant: Instant): String {
        // Convert Instant to Java's Date
        val date = Date.from(instant.toJavaInstant())
        return simpleDateFormat.format(date)
    }

    override fun parse(value: String): Instant? {
        return try {
            val date = simpleDateFormat.parse(value)
            // Convert Java Date back to Instant
            date?.toInstant()?.toKotlinInstant()

        } catch (e: ParseException) {
            null
        }
    }
}
