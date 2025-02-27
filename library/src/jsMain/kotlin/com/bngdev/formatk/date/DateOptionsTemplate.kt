package com.bngdev.formatk.date

import luxon.DateTimeOptions
import luxon.FromFormatOptions
import luxon.ToLocaleStringOptions

@Suppress("UNCHECKED_CAST_TO_EXTERNAL_INTERFACE")
actual fun toLocaleStringTemplate(): ToLocaleStringOptions = js("({})") as ToLocaleStringOptions

@Suppress("UNCHECKED_CAST_TO_EXTERNAL_INTERFACE")
actual fun dateTimeOptionsTemplate(): DateTimeOptions = js("({})") as DateTimeOptions

@Suppress("UNCHECKED_CAST_TO_EXTERNAL_INTERFACE")
actual fun fromFormatOptionsTemplate(): FromFormatOptions = js("({})") as FromFormatOptions

