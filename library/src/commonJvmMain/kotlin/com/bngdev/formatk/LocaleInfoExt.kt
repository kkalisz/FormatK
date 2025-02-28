package com.bngdev.formatk

import java.util.Locale

// TODO check version of java if possible take into consideration android  (Locale.of)
fun LocaleInfo.toLocale(): Locale = Locale(this.language, this.region)
