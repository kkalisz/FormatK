package com.bngdev.formatk.date

import luxon.DateTimeOptions
import luxon.FromFormatOptions
import luxon.ToLocaleStringOptions

expect fun toLocaleStringTemplate(): ToLocaleStringOptions

expect fun dateTimeOptionsTemplate(): DateTimeOptions

expect fun fromFormatOptionsTemplate(): FromFormatOptions

