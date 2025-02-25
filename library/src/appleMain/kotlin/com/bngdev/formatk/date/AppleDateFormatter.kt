package com.bngdev.formatk.date

import kotlinx.datetime.Instant
import kotlinx.datetime.toKotlinInstant
import kotlinx.datetime.toNSDate
import platform.Foundation.NSDateFormatter

class AppleDateFormatter(
    private val dateFormatter: NSDateFormatter
) : DateFormatter {

    override fun format(instant: Instant): String {
        val date = instant.toNSDate()
        return dateFormatter.stringFromDate(date)
    }

    override fun parse(value: String): Instant? {
        val date = dateFormatter.dateFromString(value)
        return date?.toKotlinInstant()
    }
}
