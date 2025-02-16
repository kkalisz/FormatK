package com.bngdev.formatk.number

import com.bngdev.formatk.LocaleInfo

actual object NumberFormaterProvider {
    actual fun getInstance(locale: LocaleInfo): NumberFormatFactory {
        return JSNumberFormaterFactory(locale)
    }
}
