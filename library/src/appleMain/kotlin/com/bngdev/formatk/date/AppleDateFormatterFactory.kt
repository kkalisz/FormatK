package com.bngdev.formatk.date

import com.bngdev.formatk.LocaleInfo
import com.bngdev.formatk.number.toNSLocale
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSDateFormatterFullStyle
import platform.Foundation.NSDateFormatterLongStyle
import platform.Foundation.NSDateFormatterMediumStyle
import platform.Foundation.NSDateFormatterNoStyle
import platform.Foundation.NSDateFormatterShortStyle
import platform.Foundation.NSDateFormatterStyle
import platform.Foundation.NSTimeZone
import platform.Foundation.localTimeZone
import platform.Foundation.timeZoneWithName

class AppleDateFormatterFactory(private val localeInfo: LocaleInfo) : DateFormatterFactory {

    override fun getFormatter(formatOptions: DateFormatterSettings): DateFormatter {
        val dateFormatter = NSDateFormatter()

        // Set the locale
        dateFormatter.locale = localeInfo.toNSLocale()

        // Apply date and time styles based on format mode
        if (formatOptions.pattern != null) {
            dateFormatter.dateFormat = formatOptions.pattern
        } else {
            dateFormatter.dateStyle = formatOptions.dateStyle.toNSDateFormatterStyle()
            dateFormatter.timeStyle = formatOptions.timeStyle.toNSDateFormatterStyle()
        }

        // TimeZone handling
        formatOptions.timeZone?.let { timeZone ->
            val nsTimeZone = NSTimeZone.timeZoneWithName(timeZone.id) ?: NSTimeZone.localTimeZone
            dateFormatter.timeZone = nsTimeZone
        }

        return AppleDateFormatter(dateFormatter)
    }
}

// Helper extensions to convert FormatStyle to NSDateFormatterStyle
private fun FormatStyle.toNSDateFormatterStyle(): NSDateFormatterStyle {
    return when (this) {
        FormatStyle.SHORT -> NSDateFormatterShortStyle
        FormatStyle.MEDIUM -> NSDateFormatterMediumStyle
        FormatStyle.LONG -> NSDateFormatterLongStyle
        FormatStyle.FULL -> NSDateFormatterFullStyle
        FormatStyle.NONE -> NSDateFormatterNoStyle
    }
}
