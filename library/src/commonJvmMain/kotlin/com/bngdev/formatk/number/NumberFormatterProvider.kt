package com.bngdev.formatk.number

import com.bngdev.formatk.LocaleInfo

actual object NumberFormatterProvider {
    actual fun getInstance(locale: LocaleInfo): NumberFormatFactory {
        return JvmNumberFormatFactory(locale)
    }
}
