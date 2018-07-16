/*
 * Copyright (c) 2018 Voonder
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.voonapp.boilerplate.extensions

import java.text.SimpleDateFormat
import java.util.*

/**
 * The `DatePatternFormat` class is an enumeration class that provides pattern to format [String] or [Date] to [String].
 */
enum class DatePatternFormat(val pattern: String) {
    /**
     * Format with the pattern `yyyy-MM-dd'T'HH:mm:ssZ`.
     */
    RFC_822("yyyy-MM-dd'T'HH:mm:ssZ"),

    /**
     * Format with the pattern `yyyy-MM-dd'T'HH:mm:ss.SSS'Z'`.
     */
    ISO_8601("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"),

    /**
     * Format with the pattern `yyyy-MM-dd'T'HH:mm:ss'Z'`.
     */
    ISO_8601_SHORT("yyyy-MM-dd'T'HH:mm:ss'Z'"),

    /**
     * Format with the pattern `yyyy-MM-dd HH:mm:ss`.
     */
    DATE_TIME_FORMAT("yyyy-MM-dd HH:mm:ss"),

    /**
     * Format with the pattern `EEEE d MMMM`.
     */
    FULL_DATE_FORMAT("EEEE d MMMM"),

    /**
     * Format with the pattern `E d MMMM`.
     */
    LONG_DATE_FORMAT("E d MMMM"),

    /**
     * Format with the pattern `dd MMM yyyy`.
     */
    LONG_DATE_FORMAT_D_M_Y("dd MMM yyyy"),

    /**
     * Format with the pattern `dd MMM`.
     */
    LONG_DATE_FORMAT_D_M("dd MMM"),

    /**
     * Format with the pattern `dd-MM-yyyy`.
     */
    SHORT_DATE_FORMAT_D_M_Y("dd-MM-yyyy"),

    /**
     * Format with the pattern `MM-yyyy`.
     */
    SHORT_DATE_FORMAT_M_Y("MM-yyyy"),

    /**
     * Format with the pattern `dd-MM`.
     */
    SHORT_DATE_FORMAT_D_M("dd-MM"),

    /**
     * Format with the pattern `yyyy-MM-dd`.
     */
    SHORT_DATE_FORMAT_Y_M_D("yyyy-MM-dd"),

    /**
     * Format with the pattern `yyyy-MM`.
     */
    SHORT_DATE_FORMAT_Y_M("yyyy-MM"),

    /**
     * Format with the pattern `MM-dd`.
     */
    SHORT_DATE_FORMAT_M_D("MM-dd"),

    /**
     * Format with the pattern `HH:mm:ss.SSS`.
     */
    FULL_TIME_FORMAT("HH:mm:ss.SSS"),

    /**
     * Format with the pattern `HH:mm:ss`.
     */
    LONG_TIME_FORMAT("HH:mm:ss"),

    /**
     * Format with the pattern `HH:mm`.
     */
    TIME_FORMAT("HH:mm")
}

private const val DATE_SEPARATOR = '-'
private const val TIME_SEPARATOR = ':'

fun String.formatWithPattern(
    stringPatternFormat: DatePatternFormat,
    datePatternFormat: DatePatternFormat,
    stringDateSeparator: Char = DATE_SEPARATOR,
    stringTimeSeparator: Char = TIME_SEPARATOR,
    dateDateSeparator: Char = DATE_SEPARATOR,
    dateTimeSeparator: Char = TIME_SEPARATOR
): String {
    return formatWithPattern(
        stringPatternFormat.pattern,
        datePatternFormat.pattern,
        stringDateSeparator,
        stringTimeSeparator,
        dateDateSeparator,
        dateTimeSeparator
    )
}

fun String.formatWithPattern(
    stringPattern: String,
    datePattern: String,
    stringDateSeparator: Char = DATE_SEPARATOR,
    stringTimeSeparator: Char = TIME_SEPARATOR,
    dateDateSeparator: Char = DATE_SEPARATOR,
    dateTimeSeparator: Char = TIME_SEPARATOR
): String {
    val date = parseWithPattern(stringPattern, stringDateSeparator, stringTimeSeparator)
    return date.formatWithPattern(datePattern, dateDateSeparator, dateTimeSeparator)
}

fun String.parseWithPattern(
    patternFormat: DatePatternFormat,
    dateSeparator: Char = DATE_SEPARATOR,
    timeSeparator: Char = TIME_SEPARATOR
): Date {
    return parseWithPattern(patternFormat.pattern, dateSeparator, timeSeparator)
}

fun String.parseWithPattern(
    pattern: String,
    dateSeparator: Char = DATE_SEPARATOR,
    timeSeparator: Char = TIME_SEPARATOR
): Date {
    val patternClean = replaceCharacters(pattern, dateSeparator, timeSeparator)
    val format = getSimpleDateFormat(patternClean)
    return format.parse(this)
}

fun Date.formatWithPattern(
    patternFormat: DatePatternFormat,
    dateSeparator: Char = DATE_SEPARATOR,
    timeSeparator: Char = TIME_SEPARATOR
): String {
    return formatWithPattern(patternFormat.pattern, dateSeparator, timeSeparator)
}

fun Date.formatWithPattern(
    pattern: String,
    dateSeparator: Char = DATE_SEPARATOR,
    timeSeparator: Char = TIME_SEPARATOR
): String {
    val patternClean = replaceCharacters(pattern, dateSeparator, timeSeparator)
    val format = getSimpleDateFormat(patternClean)
    return format.format(this)
}

private fun replaceCharacters(pattern: String, dateSeparator: Char, timeSeparator: Char): String {
    var replacePattern = pattern

    if (dateSeparator != DATE_SEPARATOR) {
        replacePattern = pattern.replace(DATE_SEPARATOR, dateSeparator)
    }

    if (timeSeparator != TIME_SEPARATOR) {
        replacePattern = pattern.replace(TIME_SEPARATOR, timeSeparator)
    }

    return replacePattern
}

private fun getSimpleDateFormat(pattern: String): SimpleDateFormat {
    val format = SimpleDateFormat(pattern, Locale.getDefault())
    format.timeZone = TimeZone.getDefault()
    return format
}
