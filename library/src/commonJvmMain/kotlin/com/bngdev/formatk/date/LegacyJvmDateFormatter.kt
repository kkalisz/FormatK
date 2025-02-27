package com.bngdev.formatk.date

import com.bngdev.formatk.utils.normalizeWhitespaces
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toKotlinInstant
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

class LegacyJvmDateFormatter(private val simpleDateFormat: SimpleDateFormat) : DateFormatter {

    override fun format(instant: Instant): String {
        val date = Date.from(instant.toJavaInstant())
        return simpleDateFormat.format(date).normalizeWhitespaces()
    }

    override fun parse(value: String): Instant? {
        return try {
            val date = simpleDateFormat.parse(value)
            date?.toInstant()?.toKotlinInstant()

        } catch (e: ParseException) {
            null
        }
    }
}
