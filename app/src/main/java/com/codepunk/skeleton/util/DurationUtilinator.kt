package com.codepunk.skeleton.util

import java.text.DecimalFormat
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit
import kotlin.time.DurationUnit.*
import kotlin.time.toDuration

fun Duration.toElapsedTimeString(
    targetDurationUnit: DurationUnit
): String = if (isInfinite()) {
    toString()
} else absoluteValue.toComponents { days, hours, minutes, seconds, nanoseconds ->
    val durationUnits = listOf(DAYS, HOURS, MINUTES, SECONDS, NANOSECONDS)
    val durations = listOf(days, hours, minutes, seconds, nanoseconds)
    val durationsByDurationUnit = durationUnits.zip(durations) { unit, value ->
        unit.coerceIn(SECONDS, targetDurationUnit) to value.toLong().toDuration(unit)
    }.groupBy { (durationUnit, _) ->
        durationUnit
    }.mapValues { (_, pairs) ->
        pairs.map { (_, duration) -> duration }
            .reduce { acc, duration -> acc + duration }
    }

    val defaultFormat = DecimalFormat("0.#########")
    val paddedFormat = DecimalFormat("00.#########")
    buildString {
        durationUnits.forEach { unit ->
            durationsByDurationUnit[unit]?.let { duration ->
                val amount = duration.toDouble(unit)
                if (isEmpty()) {
                    append(defaultFormat.format(amount))
                } else {
                    append(':')
                    append(paddedFormat.format(amount))
                }
            }
        }
    }
}

fun parseElapsedTimeString(value: String): Duration {
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