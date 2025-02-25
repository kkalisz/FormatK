package com.bngdev.formatk.date

import kotlinx.datetime.Instant

interface DateFormatter {
    fun format(
        instant: Instant
    ): String

    fun parse(
        value: String
    ): Instant?
}