package com.codepunk.skeleton.util

import java.text.DecimalFormat
import kotlin.math.sign
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.nanoseconds
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

private val defaultFormat by lazy { DecimalFormat("0.#########") }
private val paddedFormat by lazy { DecimalFormat("00.#########") }

private fun elapsedTimeString(
    isNegative: Boolean = false,
    days: Long = 0,
    hours: Long = 0,
    minutes: Long = 0,
    seconds: Double = 0.0
): String = buildString {
    listOf(days, hours, minutes, seconds)
        .map { it.toDouble() }
        .forEachIndexed { index, amount ->
        if (isNotEmpty()) {
            append(':')
            append(paddedFormat.format(amount))
        } else if (sign(amount) == 1.0 || index > 1) {
            append(defaultFormat.format(amount))
        }
    }
    if (isNegative) insert(0, "-")
}

private fun Int.withNanoseconds(nanoseconds: Int): Double =
    (seconds + nanoseconds.nanoseconds).toDouble(DurationUnit.SECONDS)

private fun Long.withNanoseconds(nanoseconds: Int): Double =
    (seconds + nanoseconds.nanoseconds).toDouble(DurationUnit.SECONDS)

fun Duration.toElapsedTimeString(
    durationUnit: DurationUnit = DurationUnit.DAYS
): String = when {
    isInfinite() -> toString()
    this == Duration.ZERO -> "0:00"
    else -> {
        when (durationUnit) {
            DurationUnit.DAYS -> {
                absoluteValue.toComponents { days, hours, minutes, seconds, nanoseconds ->
                    elapsedTimeString(
                        isNegative = isNegative(),
                        days = days,
                        hours = hours.toLong(),
                        minutes = minutes.toLong(),
                        seconds = seconds.withNanoseconds(nanoseconds)
                    )
                }
            }

            DurationUnit.HOURS -> {
                absoluteValue.toComponents { hours, minutes, seconds, nanoseconds ->
                    elapsedTimeString(
                        isNegative = isNegative(),
                        hours = hours,
                        minutes = minutes.toLong(),
                        seconds = seconds.withNanoseconds(nanoseconds)
                    )
                }
            }

            DurationUnit.MINUTES -> {
                absoluteValue.toComponents { minutes, seconds, nanoseconds ->
                    elapsedTimeString(
                        isNegative = isNegative(),
                        minutes = minutes,
                        seconds = seconds.withNanoseconds(nanoseconds)
                    )
                }
            }

            else -> {
                absoluteValue.toComponents { seconds, nanoseconds ->
                    elapsedTimeString(
                        isNegative = isNegative(),
                        seconds = seconds.withNanoseconds(nanoseconds)
                    )
                }
            }
        }
    }

}

fun fromElapsedTimeString(value: String): Duration {
    if (value.isEmpty()) throw IllegalArgumentException("The string is empty")
    var startIndex = 0
    val infinityString = "Infinity"
    when (value[startIndex]) {
        '+', '-' -> startIndex++
    }
    val hasSign = startIndex > 0
    val isNegative = hasSign && value.startsWith('-')
    return when {
        value.length <= startIndex -> throw IllegalArgumentException("No components")
        value.regionMatches(
            thisOffset = startIndex,
            other = infinityString,
            otherOffset = 0,
            length = maxOf(value.length - startIndex, infinityString.length),
            ignoreCase = true
        ) -> Duration.INFINITE
        else -> {
            value.substring(startIndex)
                .split(':')
                .map { it.ifEmpty { "0" } }
                .reversed()
                .foldIndexed(Duration.ZERO) { index, acc, component ->
                    acc + when (index) {
                        1 -> component.toLong().minutes
                        2 -> component.toLong().hours
                        3 -> component.toLong().days
                        else -> component.toDouble().seconds
                    }
                }
        }
    }.let { if (isNegative) -it else it }
}