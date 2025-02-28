package com.bngdev.formatk.date

import luxon.DateTimeOptions
import luxon.FromFormatOptions
import luxon.ToLocaleStringOptions

actual fun toLocaleStringTemplate(): ToLocaleStringOptions = js("({})")

actual fun dateTimeOptionsTemplate(): DateTimeOptions = js("({})")

actual fun fromFormatOptionsTemplate(): FromFormatOptions = js("({})")

