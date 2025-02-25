package com.bngdev.formatk.date

import com.bngdev.formatk.LocaleInfo

actual object DateFormatterProvider {

    private val isJavaTimeSupported: Boolean by lazy {
        @Suppress("SwallowedException")
        try {
            Class.forName("java.time.format.DateTimeFormatter")
            true
        } catch (e: ClassNotFoundException) {
            false
        }
    }

    actual fun getInstance(locale: LocaleInfo): DateFormatterFactory {
        return if (isJavaTimeSupported) {
            JvmDateFormatterFactory(locale)
        } else {
            LegacyJvmDateFormatterFactory(locale)
        }
    }
}
