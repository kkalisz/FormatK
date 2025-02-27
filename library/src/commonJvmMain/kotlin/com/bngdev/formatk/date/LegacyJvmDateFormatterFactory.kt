package com.bngdev.formatk.date

import com.bngdev.formatk.LocaleInfo
import com.bngdev.formatk.toLocale
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone
import com.bngdev.formatk.date.FormatStyle as KotlinFormatStyle

class LegacyJvmDateFormatterFactory(
    private val locale: LocaleInfo
) : DateFormatterFactory {

    override fun getFormatter(formatOptions: DateFormatterSettings): DateFormatter {
        val pattern = getDefaultPattern(formatOptions, locale.toLocale())

        val formatter = SimpleDateFormat(pattern, locale.toLocale()).apply {
            timeZone =  getZoneId(formatOptions)
        }

        return LegacyJvmDateFormatter(formatter)
    }

    private fun getZoneId(formatOptions: DateFormatterSettings): TimeZone? {
        return formatOptions.timeZone?.let { TimeZone.getTimeZone(it.id) } ?: TimeZone.getDefault()
    }

    private fun toLegacyStyle(style: KotlinFormatStyle): Int {
        return when (style) {
            KotlinFormatStyle.SHORT -> DateFormat.SHORT
            KotlinFormatStyle.MEDIUM -> DateFormat.MEDIUM
            KotlinFormatStyle.LONG -> DateFormat.LONG
            KotlinFormatStyle.FULL -> DateFormat.FULL
            KotlinFormatStyle.NONE -> DateFormat.DEFAULT // fallback, ignored usage
        }
    }

    private fun getDefaultPattern(settings: DateFormatterSettings, locale: Locale): String {
        if (settings.pattern != null) return settings.pattern

        val dateStyle = toLegacyStyle(settings.dateStyle)
        val timeStyle = toLegacyStyle(settings.timeStyle)
        val formatMode = settings.getFormatMode()

        val formatter = when (formatMode) {
            DateTimeFormatMode.DATE_ONLY -> DateFormat.getDateInstance(dateStyle, locale)
            DateTimeFormatMode.TIME_ONLY -> DateFormat.getTimeInstance(timeStyle, locale)
            DateTimeFormatMode.DATE_TIME -> DateFormat.getDateTimeInstance(dateStyle, timeStyle, locale)
        } as SimpleDateFormat

        return formatter.toPattern()
    }
}
