package com.codepunk.skeleton.util

import java.text.DecimalFormat
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.DurationUnit.DAYS
import kotlin.time.DurationUnit.HOURS
import kotlin.time.DurationUnit.MINUTES
import kotlin.time.DurationUnit.NANOSECONDS
import kotlin.time.DurationUnit.SECONDS
import kotlin.time.toDuration

fun Duration.toElapsedTimeString(target: DurationUnit): String =
    if (isInfinite()) {
        toString()
    } else absoluteValue.toComponents { days, hours, minutes, seconds, nanoseconds ->
        val durationsByUnit = listOf(DAYS, HOURS, MINUTES, SECONDS, NANOSECONDS).zip(
            listOf(days, hours, minutes, seconds, nanoseconds)
        ) { unit, value ->
            unit.coerceIn(SECONDS, target) to value.toLong().toDuration(unit)
        }.groupBy { (durationUnit, _) ->
            durationUnit
        }.mapValues { (_, pairs) ->
            pairs.map { (_, duration) -> duration }
                .reduce { acc, duration -> acc + duration }
        }

        durationsByUnit.mapNotNull { (unit, duration) ->
            if (unit == SECONDS) duration.toDouble(unit)
            else duration.toLong(unit)
        }.mapIndexed { index, num ->
            val pattern = if (index == 0) "0" else "00"
            DecimalFormat("$pattern.#########").format(num)
        }.joinToString(":")
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

        else -> listOf(SECONDS, MINUTES, HOURS, DAYS).zip(
            value.substring(startIndex)
                .split(':')
                .map { it.ifEmpty { "0" } }
                .reversed()
        ).map { (unit, component) ->
            component.toDouble().toDuration(unit)
        }.reduce { acc, duration ->
            acc + duration
        }
    }.let { if (isNegative) -it else it }
}