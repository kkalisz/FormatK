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
        val zone = getZoneId(formatOptions)
        val pattern = getDefaultPattern(formatOptions, locale.toLocale())

        val formatter = SimpleDateFormat(pattern, locale.toLocale()).apply {
            timeZone = zone?.let { TimeZone.getTimeZone(it.id) } ?: TimeZone.getDefault()
        }

        return LegacyJvmDateFormatter(formatter)
    }

    private fun getZoneId(formatOptions: DateFormatterSettings): TimeZone? {
        return formatOptions.timeZone?.let { TimeZone.getTimeZone(it.id) } ?: TimeZone.getDefault()
    }

    private fun KotlinFormatStyle.toLegacyStyle(): Int {
        return when (this) {
            KotlinFormatStyle.SHORT -> DateFormat.SHORT
            KotlinFormatStyle.MEDIUM -> DateFormat.MEDIUM
            KotlinFormatStyle.LONG -> DateFormat.LONG
            KotlinFormatStyle.FULL -> DateFormat.FULL
            KotlinFormatStyle.NONE -> DateFormat.DEFAULT // Fallback, ignored in Java 7
        }
    }

    private fun getDefaultPattern(settings: DateFormatterSettings, locale: Locale): String {
        if (settings.pattern != null) return settings.pattern

        val dateStyle = settings.dateStyle.toLegacyStyle()
        val timeStyle = settings.timeStyle.toLegacyStyle()
        val formatMode = settings.getFormatMode()

        val formatter = when (formatMode) {
            DateTimeFormatMode.DATE_ONLY -> DateFormat.getDateInstance(dateStyle, locale)
            DateTimeFormatMode.TIME_ONLY -> DateFormat.getTimeInstance(timeStyle, locale)
            DateTimeFormatMode.DATE_TIME -> DateFormat.getDateTimeInstance(dateStyle, timeStyle, locale)
        } as SimpleDateFormat

        return formatter.toPattern()
    }
}
