package com.bngdev.formatk

data class CurrencyInfo(val name: String, val code: String, val symbol: String?, val fractionDigits: Int) {
    companion object {

        private val supportedLocaleTagDelimiters = listOf("-", "_")

        private fun parseWithDelimiter(input: String, delimiter: String): String? =
            input.split(delimiter)
                .takeIf { it.size == 2 }
                ?.last()
                ?.uppercase()

        private fun getCountryCodeByLocaleTag(localeTag: String): String {
            return supportedLocaleTagDelimiters.firstNotNullOfOrNull {
                    delimiter ->
                parseWithDelimiter(localeTag, delimiter)
            } ?: localeTag.uppercase()
        }

        private fun getCurrencyByCountryCode(countryCode: String): CurrencyInfo? {
            val currencyCode = countryCurrencyMap[countryCode]
            return currencyCode?.let { currencyMap[it] }
        }

        fun getCurrency(locale: String): CurrencyInfo? {
            val countryCode = getCountryCodeByLocaleTag(locale)
            return getCurrencyByCountryCode(countryCode)
        }

        fun getCurrency(locale: LocaleInfo): CurrencyInfo? {
            return getCurrencyByCountryCode(locale.region)
        }

        fun getCurrencyByCode(currencyCode: String): CurrencyInfo? {
            return currencyMap[currencyCode.uppercase()]
        }

        fun getLocalesByCurrencyCode(currencyCode: String): List<String> {
            val upperCaseCurrencyCode = currencyCode.uppercase()
            return countryCurrencyMap.filter { it.value == upperCaseCurrencyCode }.map { it.key }
        }
    }
}

private val currencyMap: Map<String, CurrencyInfo> = mapOf(
    "AED" to CurrencyInfo(name = "United Arab Emirates dirham", code = "AED", symbol = "د.إ", fractionDigits = 2),
    "AFN" to CurrencyInfo(name = "Afghan afghani", code = "AFN", symbol = "؋", fractionDigits = 2),
    "ALL" to CurrencyInfo(name = "Albanian lek", code = "ALL", symbol = "L", fractionDigits = 2),
    "AMD" to CurrencyInfo(name = "Armenian dram", code = "AMD", symbol = "֏", fractionDigits = 2),
    "ANG" to CurrencyInfo(name = "Netherlands Antillean guilder", code = "ANG", symbol = "ƒ", fractionDigits = 2),
    "AOA" to CurrencyInfo(name = "Angolan kwanza", code = "AOA", symbol = "Kz", fractionDigits = 2),
    "ARS" to CurrencyInfo(name = "Argentine peso", code = "ARS", symbol = "$", fractionDigits = 2),
    "AUD" to CurrencyInfo(name = "Australian dollar", code = "AUD", symbol = "$", fractionDigits = 2),
    "AWG" to CurrencyInfo(name = "Aruban florin", code = "AWG", symbol = "ƒ", fractionDigits = 2),
    "AZN" to CurrencyInfo(name = "Azerbaijani manat", code = "AZN", symbol = "₼", fractionDigits = 2),
    "BAM" to CurrencyInfo(
        name = "Bosnia and Herzegovina convertible mark",
        code = "BAM",
        symbol = "KM",
        fractionDigits = 2
    ),
    "BBD" to CurrencyInfo(name = "Barbados dollar", code = "BBD", symbol = "$", fractionDigits = 2),
    "BDT" to CurrencyInfo(name = "Bangladeshi taka", code = "BDT", symbol = "৳", fractionDigits = 2),
    "BGN" to CurrencyInfo(name = "Bulgarian lev", code = "BGN", symbol = "лв", fractionDigits = 2),
    "BHD" to CurrencyInfo(name = "Bahraini dinar", code = "BHD", symbol = ".د.ب", fractionDigits = 3),
    "BIF" to CurrencyInfo(name = "Burundian franc", code = "BIF", symbol = "Fr", fractionDigits = 0),
    "BMD" to CurrencyInfo(name = "Bermudian dollar", code = "BMD", symbol = "$", fractionDigits = 2),
    "BND" to CurrencyInfo(name = "Brunei dollar", code = "BND", symbol = "$", fractionDigits = 2),
    "BOB" to CurrencyInfo(name = "Boliviano", code = "BOB", symbol = "Bs.", fractionDigits = 2),
    "BOV" to CurrencyInfo(name = "Bolivian Mvdol (funds code)", code = "BOV", symbol = null, fractionDigits = 2),
    "BRL" to CurrencyInfo(name = "Brazilian real", code = "BRL", symbol = "R$", fractionDigits = 2),
    "BSD" to CurrencyInfo(name = "Bahamian dollar", code = "BSD", symbol = "$", fractionDigits = 2),
    "BTN" to CurrencyInfo(name = "Bhutanese ngultrum", code = "BTN", symbol = "Nu.", fractionDigits = 2),
    "BWP" to CurrencyInfo(name = "Botswana pula", code = "BWP", symbol = "P", fractionDigits = 2),
    "BYN" to CurrencyInfo(name = "Belarusian ruble", code = "BYN", symbol = "Br", fractionDigits = 2),
    "BZD" to CurrencyInfo(name = "Belize dollar", code = "BZD", symbol = "$", fractionDigits = 2),
    "CAD" to CurrencyInfo(name = "Canadian dollar", code = "CAD", symbol = "$", fractionDigits = 2),
    "CDF" to CurrencyInfo(name = "Congolese franc", code = "CDF", symbol = "Fr", fractionDigits = 2),
    "CHE" to CurrencyInfo(name = "WIR euro (complementary currency)", code = "CHE", symbol = null, fractionDigits = 2),
    "CHF" to CurrencyInfo(name = "Swiss franc", code = "CHF", symbol = "Fr", fractionDigits = 2),
    "CHW" to CurrencyInfo(name = "WIR franc (complementary currency)", code = "CHW", symbol = null, fractionDigits = 2),
    "CLF" to CurrencyInfo(name = "Unidad de Fomento (funds code)", code = "CLF", symbol = null, fractionDigits = 4),
    "CLP" to CurrencyInfo(name = "Chilean peso", code = "CLP", symbol = "$", fractionDigits = 0),
    "CNY" to CurrencyInfo(name = "Chinese yuan", code = "CNY", symbol = "¥", fractionDigits = 2),
    "COP" to CurrencyInfo(name = "Colombian peso", code = "COP", symbol = "$", fractionDigits = 2),
    "COU" to CurrencyInfo(name = "Unidad de Valor Real", code = "COU", symbol = null, fractionDigits = 2),
    "CRC" to CurrencyInfo(name = "Costa Rican colon", code = "CRC", symbol = "₡", fractionDigits = 2),
    "CUC" to CurrencyInfo(name = "Cuban convertible peso", code = "CUC", symbol = "$", fractionDigits = 2),
    "CUP" to CurrencyInfo(name = "Cuban peso", code = "CUP", symbol = "$", fractionDigits = 2),
    "CVE" to CurrencyInfo(name = "Cape Verdean escudo", code = "CVE", symbol = "Esc", fractionDigits = 2),
    "CZK" to CurrencyInfo(name = "Czech koruna", code = "CZK", symbol = "Kč", fractionDigits = 2),
    "DJF" to CurrencyInfo(name = "Djiboutian franc", code = "DJF", symbol = "Fr", fractionDigits = 0),
    "DKK" to CurrencyInfo(name = "Danish krone", code = "DKK", symbol = "kr", fractionDigits = 2),
    "DOP" to CurrencyInfo(name = "Dominican peso", code = "DOP", symbol = "$", fractionDigits = 2),
    "DZD" to CurrencyInfo(name = "Algerian dinar", code = "DZD", symbol = "د.ج", fractionDigits = 2),
    "EGP" to CurrencyInfo(name = "Egyptian pound", code = "EGP", symbol = "£", fractionDigits = 2),
    "ERN" to CurrencyInfo(name = "Eritrean nakfa", code = "ERN", symbol = "Nfk", fractionDigits = 2),
    "ETB" to CurrencyInfo(name = "Ethiopian birr", code = "ETB", symbol = "Br", fractionDigits = 2),
    "EUR" to CurrencyInfo(name = "Euro", code = "EUR", symbol = "€", fractionDigits = 2),
    "FJD" to CurrencyInfo(name = "Fiji dollar", code = "FJD", symbol = "$", fractionDigits = 2),
    "FKP" to CurrencyInfo(name = "Falkland Islands pound", code = "FKP", symbol = "£", fractionDigits = 2),
    "GBP" to CurrencyInfo(name = "Pound sterling", code = "GBP", symbol = "£", fractionDigits = 2),
    "GEL" to CurrencyInfo(name = "Georgian lari", code = "GEL", symbol = "₾", fractionDigits = 2),
    "GHS" to CurrencyInfo(name = "Ghanaian cedi", code = "GHS", symbol = "₵", fractionDigits = 2),
    "GIP" to CurrencyInfo(name = "Gibraltar pound", code = "GIP", symbol = "£", fractionDigits = 2),
    "GMD" to CurrencyInfo(name = "Gambian dalasi", code = "GMD", symbol = "D", fractionDigits = 2),
    "GNF" to CurrencyInfo(name = "Guinean franc", code = "GNF", symbol = "Fr", fractionDigits = 0),
    "GTQ" to CurrencyInfo(name = "Guatemalan quetzal", code = "GTQ", symbol = "Q", fractionDigits = 2),
    "GYD" to CurrencyInfo(name = "Guyanese dollar", code = "GYD", symbol = "$", fractionDigits = 2),
    "HKD" to CurrencyInfo(name = "Hong Kong dollar", code = "HKD", symbol = "$", fractionDigits = 2),
    "HNL" to CurrencyInfo(name = "Honduran lempira", code = "HNL", symbol = "L", fractionDigits = 2),
    "HRK" to CurrencyInfo(name = "Croatian kuna", code = "HRK", symbol = "kn", fractionDigits = 2),
    "HTG" to CurrencyInfo(name = "Haitian gourde", code = "HTG", symbol = "G", fractionDigits = 2),
    "HUF" to CurrencyInfo(name = "Hungarian forint", code = "HUF", symbol = "Ft", fractionDigits = 2),
    "IDR" to CurrencyInfo(name = "Indonesian rupiah", code = "IDR", symbol = "Rp", fractionDigits = 2),
    "ILS" to CurrencyInfo(name = "Israeli new shekel", code = "ILS", symbol = "₪", fractionDigits = 2),
    "INR" to CurrencyInfo(name = "Indian rupee", code = "INR", symbol = "₹", fractionDigits = 2),
    "IQD" to CurrencyInfo(name = "Iraqi dinar", code = "IQD", symbol = "ع.د", fractionDigits = 3),
    "IRR" to CurrencyInfo(name = "Iranian rial", code = "IRR", symbol = "﷼", fractionDigits = 2),
    "ISK" to CurrencyInfo(name = "Icelandic króna", code = "ISK", symbol = "kr", fractionDigits = 0),
    "JMD" to CurrencyInfo(name = "Jamaican dollar", code = "JMD", symbol = "$", fractionDigits = 2),
    "JOD" to CurrencyInfo(name = "Jordanian dinar", code = "JOD", symbol = "د.ا", fractionDigits = 3),
    "JPY" to CurrencyInfo(name = "Japanese yen", code = "JPY", symbol = "￥", fractionDigits = 0),
    "KES" to CurrencyInfo(name = "Kenyan shilling", code = "KES", symbol = "Sh", fractionDigits = 2),
    "KGS" to CurrencyInfo(name = "Kyrgyzstani som", code = "KGS", symbol = "с", fractionDigits = 2),
    "KHR" to CurrencyInfo(name = "Cambodian riel", code = "KHR", symbol = "៛", fractionDigits = 2),
    "KMF" to CurrencyInfo(name = "Comorian franc", code = "KMF", symbol = "Fr", fractionDigits = 0),
    "KPW" to CurrencyInfo(name = "North Korean won", code = "KPW", symbol = "₩", fractionDigits = 2),
    "KRW" to CurrencyInfo(name = "South Korean won", code = "KRW", symbol = "₩", fractionDigits = 0),
    "KWD" to CurrencyInfo(name = "Kuwaiti dinar", code = "KWD", symbol = "د.ك", fractionDigits = 3),
    "KYD" to CurrencyInfo(name = "Cayman Islands dollar", code = "KYD", symbol = "$", fractionDigits = 2),
    "KZT" to CurrencyInfo(name = "Kazakhstani tenge", code = "KZT", symbol = "₸", fractionDigits = 2),
    "LAK" to CurrencyInfo(name = "Lao kip", code = "LAK", symbol = "₭", fractionDigits = 2),
    "LBP" to CurrencyInfo(name = "Lebanese pound", code = "LBP", symbol = "ل.ل", fractionDigits = 2),
    "LKR" to CurrencyInfo(name = "Sri Lankan rupee", code = "LKR", symbol = "Rs", fractionDigits = 2),
    "LRD" to CurrencyInfo(name = "Liberian dollar", code = "LRD", symbol = "$", fractionDigits = 2),
    "LSL" to CurrencyInfo(name = "Lesotho loti", code = "LSL", symbol = "L", fractionDigits = 2),
    "LYD" to CurrencyInfo(name = "Libyan dinar", code = "LYD", symbol = "ل.د", fractionDigits = 3),
    "MAD" to CurrencyInfo(name = "Moroccan dirham", code = "MAD", symbol = "د.م.", fractionDigits = 2),
    "MDL" to CurrencyInfo(name = "Moldovan leu", code = "MDL", symbol = "L", fractionDigits = 2),
    "MGA" to CurrencyInfo(name = "Malagasy ariary", code = "MGA", symbol = "Ar", fractionDigits = 2),
    "MKD" to CurrencyInfo(name = "Macedonian denar", code = "MKD", symbol = "ден", fractionDigits = 2),
    "MMK" to CurrencyInfo(name = "Burmese kyat", code = "MMK", symbol = "Ks", fractionDigits = 2),
    "MNT" to CurrencyInfo(name = "Mongolian tögrög", code = "MNT", symbol = "₮", fractionDigits = 2),
    "MOP" to CurrencyInfo(name = "Macanese pataca", code = "MOP", symbol = "P", fractionDigits = 2),
    "MRU" to CurrencyInfo(name = "Mauritanian ouguiya", code = "MRU", symbol = "UM", fractionDigits = 2),
    "MUR" to CurrencyInfo(name = "Mauritian rupee", code = "MUR", symbol = "Rs", fractionDigits = 2),
    "MVR" to CurrencyInfo(name = "Maldivian rufiyaa", code = "MVR", symbol = "Rf", fractionDigits = 2),
    "MWK" to CurrencyInfo(name = "Malawian kwacha", code = "MWK", symbol = "MK", fractionDigits = 2),
    "MXN" to CurrencyInfo(name = "Mexican peso", code = "MXN", symbol = "$", fractionDigits = 2),
    "MYR" to CurrencyInfo(name = "Malaysian ringgit", code = "MYR", symbol = "RM", fractionDigits = 2),
    "MZN" to CurrencyInfo(name = "Mozambican metical", code = "MZN", symbol = "MT", fractionDigits = 2),
    "NAD" to CurrencyInfo(name = "Namibian dollar", code = "NAD", symbol = "$", fractionDigits = 2),
    "NGN" to CurrencyInfo(name = "Nigerian naira", code = "NGN", symbol = "₦", fractionDigits = 2),
    "NIO" to CurrencyInfo(name = "Nicaraguan córdoba", code = "NIO", symbol = "C$", fractionDigits = 2),
    "NOK" to CurrencyInfo(name = "Norwegian krone", code = "NOK", symbol = "kr", fractionDigits = 2),
    "NPR" to CurrencyInfo(name = "Nepalese rupee", code = "NPR", symbol = "Rs", fractionDigits = 2),
    "NZD" to CurrencyInfo(name = "New Zealand dollar", code = "NZD", symbol = "$", fractionDigits = 2),
    "OMR" to CurrencyInfo(name = "Omani rial", code = "OMR", symbol = "ر.ع.", fractionDigits = 3),
    "PAB" to CurrencyInfo(name = "Panamanian balboa", code = "PAB", symbol = "B/.", fractionDigits = 2),
    "PEN" to CurrencyInfo(name = "Peruvian sol", code = "PEN", symbol = "S/", fractionDigits = 2),
    "PGK" to CurrencyInfo(name = "Papua New Guinean kina", code = "PGK", symbol = "K", fractionDigits = 2),
    "PHP" to CurrencyInfo(name = "Philippine peso", code = "PHP", symbol = "₱", fractionDigits = 2),
    "PKR" to CurrencyInfo(name = "Pakistani rupee", code = "PKR", symbol = "Rs", fractionDigits = 2),
    "PLN" to CurrencyInfo(name = "Polish złoty", code = "PLN", symbol = "zł", fractionDigits = 2),
    "PYG" to CurrencyInfo(name = "Paraguayan guaraní", code = "PYG", symbol = "₲", fractionDigits = 0),
    "QAR" to CurrencyInfo(name = "Qatari riyal", code = "QAR", symbol = "ر.ق", fractionDigits = 2),
    "RON" to CurrencyInfo(name = "Romanian leu", code = "RON", symbol = "lei", fractionDigits = 2),
    "RSD" to CurrencyInfo(name = "Serbian dinar", code = "RSD", symbol = "дин.", fractionDigits = 2),
    "RUB" to CurrencyInfo(name = "Russian ruble", code = "RUB", symbol = "₽", fractionDigits = 2),
    "RWF" to CurrencyInfo(name = "Rwandan franc", code = "RWF", symbol = "Fr", fractionDigits = 0),
    "SAR" to CurrencyInfo(name = "Saudi riyal", code = "SAR", symbol = "ر.س", fractionDigits = 2),
    "SBD" to CurrencyInfo(name = "Solomon Islands dollar", code = "SBD", symbol = "$", fractionDigits = 2),
    "SCR" to CurrencyInfo(name = "Seychellois rupee", code = "SCR", symbol = "Rs", fractionDigits = 2),
    "SDG" to CurrencyInfo(name = "Sudanese pound", code = "SDG", symbol = "ج.س.", fractionDigits = 2),
    "SEK" to CurrencyInfo(name = "Swedish krona", code = "SEK", symbol = "kr", fractionDigits = 2),
    "SGD" to CurrencyInfo(name = "Singapore dollar", code = "SGD", symbol = "$", fractionDigits = 2),
    "SHP" to CurrencyInfo(name = "Saint Helena pound", code = "SHP", symbol = "£", fractionDigits = 2),
    "SLE" to CurrencyInfo(name = "Sierra Leonean leone", code = "SLE", symbol = "Le", fractionDigits = 2),
    "SLL" to CurrencyInfo(name = "Sierra Leonean leone (old)", code = "SLL", symbol = "Le", fractionDigits = 2),
    "SOS" to CurrencyInfo(name = "Somali shilling", code = "SOS", symbol = "Sh", fractionDigits = 2),
    "SRD" to CurrencyInfo(name = "Surinamese dollar", code = "SRD", symbol = "$", fractionDigits = 2),
    "SSP" to CurrencyInfo(name = "South Sudanese pound", code = "SSP", symbol = "£", fractionDigits = 2),
    "STN" to CurrencyInfo(name = "São Tomé and Príncipe dobra", code = "STN", symbol = "Db", fractionDigits = 2),
    "SVC" to CurrencyInfo(name = "Salvadoran colón", code = "SVC", symbol = "₡", fractionDigits = 2),
    "SYP" to CurrencyInfo(name = "Syrian pound", code = "SYP", symbol = "ل.س", fractionDigits = 2),
    "SZL" to CurrencyInfo(name = "Eswatini lilangeni", code = "SZL", symbol = "L", fractionDigits = 2),
    "THB" to CurrencyInfo(name = "Thai baht", code = "THB", symbol = "฿", fractionDigits = 2),
    "TJS" to CurrencyInfo(name = "Tajikistani somoni", code = "TJS", symbol = "ЅМ", fractionDigits = 2),
    "TMT" to CurrencyInfo(name = "Turkmenistan manat", code = "TMT", symbol = "m", fractionDigits = 2),
    "TND" to CurrencyInfo(name = "Tunisian dinar", code = "TND", symbol = "د.ت", fractionDigits = 3),
    "TOP" to CurrencyInfo(name = "Tongan paʻanga", code = "TOP", symbol = "T$", fractionDigits = 2),
    "TRY" to CurrencyInfo(name = "Turkish lira", code = "TRY", symbol = "₺", fractionDigits = 2),
    "TTD" to CurrencyInfo(name = "Trinidad and Tobago dollar", code = "TTD", symbol = "$", fractionDigits = 2),
    "TVD" to CurrencyInfo(name = "Tuvaluan dollar", code = "TVD", symbol = "$", fractionDigits = 2),
    "TWD" to CurrencyInfo(name = "New Taiwan dollar", code = "TWD", symbol = "NT$", fractionDigits = 2),
    "TZS" to CurrencyInfo(name = "Tanzanian shilling", code = "TZS", symbol = "Sh", fractionDigits = 2),
    "UAH" to CurrencyInfo(name = "Ukrainian hryvnia", code = "UAH", symbol = "₴", fractionDigits = 2),
    "UGX" to CurrencyInfo(name = "Ugandan shilling", code = "UGX", symbol = "Sh", fractionDigits = 0),
    "USD" to CurrencyInfo(name = "United States dollar", code = "USD", symbol = "$", fractionDigits = 2),
    "UYU" to CurrencyInfo(name = "Uruguayan peso", code = "UYU", symbol = "$", fractionDigits = 2),
    "UZS" to CurrencyInfo(name = "Uzbekistani soʻm", code = "UZS", symbol = "сўм", fractionDigits = 2),
    "VES" to CurrencyInfo(name = "Venezuelan bolívar", code = "VES", symbol = "Bs", fractionDigits = 2),
    "VND" to CurrencyInfo(name = "Vietnamese đồng", code = "VND", symbol = "₫", fractionDigits = 0),
    "VUV" to CurrencyInfo(name = "Vanuatu vatu", code = "VUV", symbol = "Vt", fractionDigits = 0),
    "WST" to CurrencyInfo(name = "Samoan tālā", code = "WST", symbol = "T", fractionDigits = 2),
    "XAF" to CurrencyInfo(name = "Central African CFA franc", code = "XAF", symbol = "Fr", fractionDigits = 0),
    "XCD" to CurrencyInfo(name = "East Caribbean dollar", code = "XCD", symbol = "$", fractionDigits = 2),
    "XOF" to CurrencyInfo(name = "West African CFA franc", code = "XOF", symbol = "Fr", fractionDigits = 0),
    "XPF" to CurrencyInfo(name = "CFP franc", code = "XPF", symbol = "Fr", fractionDigits = 0),
    "YER" to CurrencyInfo(name = "Yemeni rial", code = "YER", symbol = "﷼", fractionDigits = 2),
    "ZAR" to CurrencyInfo(name = "South African rand", code = "ZAR", symbol = "R", fractionDigits = 2),
    "ZMW" to CurrencyInfo(name = "Zambian kwacha", code = "ZMW", symbol = "ZK", fractionDigits = 2),
    "ZWL" to CurrencyInfo(name = "Zimbabwean dollar", code = "ZWL", symbol = "$", fractionDigits = 2)
)

val countryCurrencyMap: Map<String, String> = mapOf(
    "AD" to "EUR", "AE" to "AED", "AF" to "AFN", "AG" to "XCD", "AI" to "XCD",
    "AL" to "ALL", "AM" to "AMD", "AO" to "AOA", "AR" to "ARS", "AS" to "USD",
    "AT" to "EUR", "AU" to "AUD", "AW" to "AWG", "AX" to "SEK", "AZ" to "AZN",
    "BA" to "BAM", "BB" to "BBD", "BD" to "BDT", "BE" to "EUR", "BF" to "CFA",
    "BG" to "BGN", "BH" to "BHD", "BI" to "BIF", "BJ" to "CFA", "BL" to "EUR",
    "BM" to "BMD", "BN" to "BND", "BO" to "BOB", "BQ" to "USD", "BR" to "BRL",
    "BS" to "BSD", "BT" to "BTN", "BV" to "NOK", "BW" to "BWP", "BY" to "BYN",
    "BZ" to "BZD", "CA" to "CAD", "CC" to "AUD", "CD" to "CDF", "CF" to "CFA",
    "CG" to "CDF", "CH" to "CHF", "CI" to "CFA", "CK" to "NZD", "CL" to "CLP",
    "CM" to "XAF", "CN" to "CNY", "CO" to "COP", "CR" to "CRC", "CU" to "CUP",
    "CV" to "CVE", "CW" to "ANG", "CX" to "AUD", "CY" to "CYP", "CZ" to "CZK",
    "DE" to "EUR", "DJ" to "DJF", "DK" to "DKK", "DM" to "DOP", "DO" to "DZD",
    "DZ" to "DZD", "EC" to "USD", "EE" to "EEK", "EG" to "EGP", "EH" to "MAD",
    "ER" to "ERN", "ES" to "ESP", "ET" to "ETB", "FI" to "EUR", "FJ" to "FJD",
    "FM" to "USD", "FO" to "DKK", "FR" to "EUR", "GA" to "CDF", "GB" to "GBP",
    "GD" to "GDS", "GE" to "GEL", "GF" to "EUR", "GG" to "GBP", "GH" to "GHS",
    "GI" to "GIP", "GL" to "DKK", "GM" to "GMD", "GN" to "GNF", "GP" to "EUR",
    "GQ" to "GNF", "GR" to "EUR", "GT" to "GTQ", "GU" to "USD", "GW" to "CFA",
    "GY" to "GYD", "HK" to "HKD", "HN" to "HNL", "HR" to "HRK", "HT" to "HTG",
    "HU" to "HUF", "ID" to "IDR", "IE" to "EUR", "IL" to "ILS", "IM" to "GBP",
    "IN" to "INR", "IO" to "USD", "IQ" to "IQD", "IR" to "IRR", "IS" to "ISK",
    "IT" to "EUR", "JE" to "GBP", "JM" to "JMD", "JO" to "JOD", "JP" to "JPY",
    "KE" to "KES", "KG" to "KGS", "KH" to "KHR", "KI" to "AUD", "KM" to "COM",
    "KN" to "XCD", "KP" to "KPW", "KR" to "KRW", "KW" to "KWD", "KY" to "KYD",
    "KZ" to "KZT", "LA" to "LAK", "LB" to "LBP", "LC" to "LCA", "LI" to "CHF",
    "LK" to "LKR", "LR" to "LRD", "LS" to "LSL", "LT" to "LTL", "LU" to "EUR",
    "LV" to "LVL", "LY" to "LYD", "MA" to "MAD", "MC" to "EUR", "MD" to "MDL",
    "ME" to "EUR", "MF" to "EUR", "MG" to "MGA", "MH" to "USD", "MK" to "MKD",
    "ML" to "CFA", "MM" to "MMK", "MN" to "MNT", "MO" to "MOP", "MP" to "USD",
    "MQ" to "EUR", "MR" to "MRU", "MS" to "XCD", "MT" to "EUR", "MU" to "MUR",
    "MV" to "MVR", "MW" to "MWK", "MX" to "MXN", "MY" to "MYR", "MZ" to "MZN",
    "NA" to "NAD", "NC" to "XPF", "NE" to "NGN", "NF" to "AUD", "NG" to "NGN",
    "NI" to "NIO", "NL" to "NLD", "NO" to "NOK", "NP" to "NPR", "NR" to "AUD",
    "NU" to "NZD", "NZ" to "NZD", "OM" to "OMR", "PA" to "PAB", "PE" to "PEN",
    "PF" to "CFP", "PG" to "PGK", "PH" to "PHP", "PK" to "PKR", "PL" to "PLN",
    "PM" to "EUR", "PN" to "NZD", "PR" to "USD", "PT" to "POR", "PW" to "USD",
    "PY" to "PYG", "QA" to "QAR", "RE" to "EUR", "RO" to "RON", "RS" to "RSD",
    "RU" to "RUB", "RW" to "RWF", "SA" to "SAR", "SB" to "SBD", "SC" to "SCR",
    "SD" to "SDG", "SE" to "SEK", "SG" to "SGD", "SH" to "SHS", "SI" to "SIT",
    "SJ" to "NOK", "SK" to "SKK", "SL" to "SLL", "SM" to "SMM", "SN" to "XOF",
    "SO" to "SOS", "SR" to "SRD", "SS" to "SSP", "ST" to "STN", "SV" to "SVC",
    "SX" to "ANG", "SY" to "SYP", "SZ" to "SZL", "TC" to "USD", "TD" to "CFA",
    "TF" to "EUR", "TG" to "TGO", "TH" to "THB", "TJ" to "TJS", "TK" to "NZD",
    "TL" to "USD", "TM" to "TMT", "TN" to "TND", "TO" to "TOP", "TR" to "TRY",
    "TT" to "TTD", "TV" to "AUD", "TZ" to "TZS", "UA" to "UAH", "UG" to "UGX",
    "UM" to "USD", "US" to "USD", "UY" to "UYU", "UZ" to "UZS", "VA" to "EUR",
    "VC" to "VEC", "VE" to "VES", "VG" to "USD", "VI" to "USD", "VN" to "VND",
    "VU" to "VUV", "WF" to "XPF", "WS" to "WST", "YE" to "YER", "YT" to "EUR",
    "ZA" to "ZAR", "ZM" to "ZMK", "ZW" to "ZWL"
)
