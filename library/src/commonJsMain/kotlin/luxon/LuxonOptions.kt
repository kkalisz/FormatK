
package luxon

expect class DateTime {
    companion object {
        fun now(): DateTime
        fun fromMillis(millis: Double): DateTime
        fun fromMillis(millis: Double, options: DateTimeOptions): DateTime
        fun fromISO(isoString: String, options: DateTimeOptions): DateTime
        fun fromFormat(text: String, format: String, options: FromFormatOptions): DateTime
    }

    fun toMillis(): Double
    fun toLocaleString(options: ToLocaleStringOptions): String
    fun toFormat(format: String): String
    fun isValid(): Boolean
}


external interface DateTimeOptions {
    var zone: String?
    var locale: String?
}

external interface ToLocaleStringOptions {
    var locale: String?
    var timeZone: String?
    var dateStyle: String?
    var timeStyle: String?
}

external interface FromFormatOptions {
    var zone: String?
    var locale: String?
}