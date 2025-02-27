@file:JsModule("luxon")

package luxon

actual external class DateTime {
    actual companion object {
        actual fun now(): DateTime
        actual fun fromMillis(millis: Double): DateTime
        actual fun fromMillis(millis: Double, options: DateTimeOptions): DateTime
        actual fun fromISO(isoString: String, options: DateTimeOptions): DateTime
        actual fun fromFormat(text: String, format: String, options: FromFormatOptions): DateTime
    }

    actual fun toMillis(): Double
    actual fun toLocaleString(options: ToLocaleStringOptions): String
    actual fun toFormat(format: String): String
    actual fun isValid(): Boolean
}