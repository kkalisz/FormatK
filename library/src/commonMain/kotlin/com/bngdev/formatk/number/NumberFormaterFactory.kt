package com.bngdev.formatk.number

import com.bngdev.formatk.LocaleInfo

expect object NumberFormaterFactory {
    fun getInstance(locale: LocaleInfo): NumberFormater
}
