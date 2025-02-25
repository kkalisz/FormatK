package com.bngdev.formatk.date

interface DateFormatterFactory {
    fun getFormatter(
        formatOptions: DateFormatterSettings
    ): DateFormatter
}
