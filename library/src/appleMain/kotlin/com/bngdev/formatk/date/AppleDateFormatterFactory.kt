package com.bngdev.formatk.date

import com.bngdev.formatk.LocaleInfo
import com.bngdev.formatk.number.toNSLocale
import kotlinx.datetime.toNSTimeZone
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

        dateFormatter.locale = localeInfo.toNSLocale()

        if (formatOptions.pattern != null) {
            dateFormatter.dateFormat = formatOptions.pattern
        } else {
            dateFormatter.dateStyle = toNSDateFormatterStyle(formatOptions.dateStyle)
            dateFormatter.timeStyle = toNSDateFormatterStyle(formatOptions.timeStyle)
        }

        formatOptions.timeZone?.let { timeZone ->
            dateFormatter.timeZone = timeZone.toNSTimeZone()
        }
        if(formatOptions.pattern == null){
            dateFormatter.dateFormat = removeQuotedWordsWithSpaceAndQuotes(dateFormatter.dateFormat)
        }

        return AppleDateFormatter(dateFormatter)
    }

    private fun removeQuotedWordsWithSpaceAndQuotes(format: String): String {
        // with iso we have often additional text inside format for example: "dd.MM.y 'at' HH:mm:ss"
        return Regex("'\\s*[^']+\\s*'" ).replace(format, "") // Replaces with an empty string
    }

    private fun toNSDateFormatterStyle(style: FormatStyle): NSDateFormatterStyle {
        return when (style) {
            FormatStyle.SHORT -> NSDateFormatterShortStyle
            FormatStyle.MEDIUM -> NSDateFormatterMediumStyle
            FormatStyle.LONG -> NSDateFormatterLongStyle
            FormatStyle.FULL -> NSDateFormatterFullStyle
            FormatStyle.NONE -> NSDateFormatterNoStyle
        }
    }
}
