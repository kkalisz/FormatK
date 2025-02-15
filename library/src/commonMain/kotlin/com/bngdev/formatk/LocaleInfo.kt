package com.bngdev.formatk

data class LocaleInfo(
    val language: String,
    val region: String,
) {
    fun toLanguageTag(validate: Boolean = false): String {
        val cleanLanguage = language.trim().lowercase()

        if (validate) {
            require(cleanLanguage.matches(Regex("^[a-z]{2,3}$"))) {
                "Language code must be 2 or 3 lowercase letters"
            }
        }

        val cleanRegion = region.trim().uppercase()
        if (validate) {
            require(cleanRegion.matches(Regex("^[A-Z]{2}$"))) {
                "Region code must be 2 uppercase letters"
            }
        }

        return buildString {
            append(cleanLanguage)
            append("-")
            append(cleanRegion)
        }
    }

    override fun toString(): String {
        return toLanguageTag(false)
    }
}
