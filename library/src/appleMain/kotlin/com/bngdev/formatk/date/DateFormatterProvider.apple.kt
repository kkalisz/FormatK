package com.bngdev.formatk.date

import com.bngdev.formatk.LocaleInfo

actual object DateFormatterProvider {
    actual fun getInstance(locale: LocaleInfo): DateFormatterFactory {
        return AppleDateFormatterFactory(locale)
    }
}
