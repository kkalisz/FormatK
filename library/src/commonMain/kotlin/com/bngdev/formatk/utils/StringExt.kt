package com.bngdev.formatk.utils

fun String.normalizeWhitespaces(): String {
    return this.replace("\\s+".toRegex(), " ").replace(' ',' ')
}