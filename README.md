# FormatK



**FormatK** is a Kotlin Multiplatform library for formatting numbers and dates based on locale. It supports JVM, Android, JavaScript (WASM & JS), and Apple platforms.

---

## Features
- 🔧 Native API Usage: Utilizes native APIs under the hood for consistent and accurate formatting across platforms.
- 📌 **Multiplatform**: Works across JVM, Android, WASM, JS, and Apple targets.
- 🌎 **Locale-aware formatting**: Provides accurate number and date formatting based on locale settings.
- 🚀 **Lightweight & Efficient**: Optimized for performance and low overhead.
---

## Supported Platforms

FormatK supports the following Kotlin Multiplatform targets:

- ✅ JVM
- ✅ Android
- ✅ JavaScript (WASM & JS)
- ✅ Apple (iOS, macOS, watchOS, tvOS)

---

## Installation

### Gradle (Kotlin DSL)

```kotlin
dependencies {
    implementation("com.bngdev.formatk:0.0.2")
}
```

### Gradle (Groovy DSL)

```groovy
dependencies {
    implementation 'com.bngdev.formatk:0.0.2'
}
```

Replace `<VERSION>` with the latest release available on [Maven Central](https://search.maven.org/search?q=g\:com.bngdev.formatk).

---


## Usage

### Number Formatting

Format numbers into human-readable formats with precision, locale-specific grouping, and customizable rules.

#### 1. Formatting Numbers (Decimal Style)

To format plain numbers based on locale:

```kotlin
import java.time.format.FormatStyle

// Instantiate a formatter for a specific locale
val localeInfo = LocaleInfo("en", "US") // English (US)
val numberFormatter = NumberFormaterProvider.getFormater(localeInfo, FormatStyle.DECIMAL)

// Format numbers as decimals
val formattedDecimal = numberFormatter.format(654321.987)
println(formattedDecimal) // Output: 654,321.987
```

#### 2. Formatting Currencies

When dealing with monetary values, FormatK can produce locale-appropriate numbers with currency symbols, decimal places, and grouping.

```kotlin
val numberFormatter = NumberFormaterProvider.getFormater(localeInfo, FormatStyle.CURRENCY)

val formattedCurrency = numberFormatter.format(654321.987)
println(formattedCurrency) // Output: $654,321.99
```

For more control, use **custom currency codes** and settings:

```kotlin
import com.bngdev.formatk.number.NumberFormaterSettings

val customCurrencySettings = NumberFormaterSettings(currencyCode = "GBP")
val numberFormatter = NumberFormaterProvider.getFormater(localeInfo, FormatStyle.CURRENCY, customCurrencySettings)

val formattedGBP = numberFormatter.format(654321.987)
println(formattedGBP) // Output: £654,321.99
```

#### 3. Formatting Percentages

To represent numbers as percentages:

```kotlin
val numberFormatter = NumberFormaterProvider.getFormater(localeInfo, FormatStyle.PERCENT)

val formattedPercentage = numberFormatter.format(0.975)

println(formattedPercentage) // Output: 97.5%
```

Percentages are automatically scaled to the `0 - 100%` range.

#### 4. Customizing Formatting

**NumberFormaterSettings** offers various options to tweak the formatting rules as per your needs:

- **useGrouping**: Enable or disable thousands separators.
- **minimumFractionDigits** and **maximumFractionDigits**: Set how many decimals to display.
- **roundingMode**: Adjust rounding (e.g., HALF_UP, FLOOR, CEILING, etc.).

Example:

```kotlin
val customSettings = NumberFormaterSettings(
    useGrouping = false, // Disable grouping (e.g., no commas)
    minimumFractionDigits = 1,
    maximumFractionDigits = 3,
    roundingMode = RoundingMode.FLOOR
)

val numberFormatter = NumberFormaterProvider.getFormater(localeInfo, FormatStyle.DECIMAL, customSettings)


val formattedNumber = numberFormatter.format(12345.6789)
println(formattedNumber) // Output: 12345.678
```

#### 5. Retrieving Default Settings

You can retrieve default formatting configurations based on a locale and desired style using the built-in interface:

```kotlin
val defaultSettings = NumberFormaterProvider.getInstance(localeInfo).getDefaultSettings(FormatStyle.CURRENCY)
println(defaultSettings.useGrouping) // Output: true
```

---

## Testing

FormatK includes a robust suite of tests to ensure seamless functionality across different locales, number styles, and edge cases. The testing framework validates multiple key areas of the library.

## Contributing

We welcome contributions!

---

## License

FormatK is released under the [Apache License, Version 2.0, January 2004](LICENSE).

---

## Contact & Support

For questions, suggestions, or issues, feel free to open an [issue](https://github.com/kkalisz/FormatK/issues) or reach out on [Discussions](https://github.com/kkalisz/FormatK/discussions).

