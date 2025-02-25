package luxon

external class DateTime {
    companion object {
        fun now(options: DateTimeOptions): DateTime
        fun fromMillis(millis: Double, options: DateTimeOptions): DateTime
        fun fromISO(isoString: String, options: DateTimeOptions): DateTime
        fun fromFormat(text: String, format: String, options: FromFormatOptions): DateTime
    }

    fun toISO(): String
    fun toMillis(): Double
    fun toLocaleString(options: ToLocaleStringOptions): String
    fun toFormat(format: String): String
    fun setZone(zone: String): DateTime
    fun setLocale(locale: String): DateTime
    fun isValid(): Boolean
}


external interface DateTimeOptions {
    var zone: String? // Time zone, e.g., "UTC", "America/New_York"
    var locale: String? // Locale, e.g., "en-US"
    var outputCalendar: String? // Calendar system, e.g., "gregory"
    var numberingSystem: String? // Numbering system, e.g., "latn"
}

external interface ToLocaleStringOptions {
    var locale: String? // e.g., "fr-FR"
    var timeZone: String? // e.g., "Asia/Tokyo"
    var dateStyle: String? // "full", "long", "medium", "short"
    var timeStyle: String? // "full", "long", "medium", "short"
    var hourCycle: String? // "h11", "h12", "h23", "h24"
}

external interface FromFormatOptions {
    var zone: String? // Time zone
    var locale: String? // Locale
    var defaultZone: Boolean? // Whether to use system default zone
}