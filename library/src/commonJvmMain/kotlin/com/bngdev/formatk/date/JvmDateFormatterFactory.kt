package com.bngdev.formatk.date

import com.bngdev.formatk.LocaleInfo
import com.bngdev.formatk.toLocale
import kotlinx.datetime.toJavaZoneId
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale
import java.util.TimeZone
import com.bngdev.formatk.date.FormatStyle as KotlinFormatStyle

class JvmDateFormatterFactory(
    private val locale: LocaleInfo
) : DateFormatterFactory {

    override fun getFormatter(formatOptions: DateFormatterSettings): DateFormatter {
        val zone = getZoneId(formatOptions)
        val formatter = getDefaultPattern(formatOptions, locale.toLocale()).withZone(zone)
        return JvmDateFormatter(formatter)
    }

    private fun getZoneId(formatOptions: DateFormatterSettings): ZoneId? {
        if (formatOptions.timeZone != null) {
            return formatOptions.timeZone.toJavaZoneId()
        }
        return TimeZone.getDefault().toZoneId()
    }

    private fun KotlinFormatStyle.toJavaFormatStyle(): FormatStyle {
        return when (this) {
            KotlinFormatStyle.SHORT -> FormatStyle.SHORT
            KotlinFormatStyle.MEDIUM -> FormatStyle.MEDIUM
            KotlinFormatStyle.LONG -> FormatStyle.LONG
            KotlinFormatStyle.FULL -> FormatStyle.FULL
            KotlinFormatStyle.NONE -> FormatStyle.FULL // TODO, but no difference as we ignore this
        }
    }

    private fun getDefaultPattern(settings: DateFormatterSettings, locale: Locale): DateTimeFormatter {
        if (settings.pattern != null) return DateTimeFormatter.ofPattern(settings.pattern)
        val dateStyle = settings.dateStyle.toJavaFormatStyle()
        val timeStyle = settings.timeStyle.toJavaFormatStyle()
        val formatMode = settings.getFormatMode()

        return when (formatMode) {
            DateTimeFormatMode.DATE_ONLY ->
                DateTimeFormatter.ofLocalizedDate(dateStyle).withLocale(locale)
            DateTimeFormatMode.TIME_ONLY ->
                DateTimeFormatter.ofLocalizedTime(timeStyle).withLocale(locale)
            DateTimeFormatMode.DATE_TIME ->
                DateTimeFormatter.ofLocalizedDateTime(dateStyle, timeStyle).withLocale(locale)
        }
    }
}
