package com.codepunk.skeleton.util

suspend fun Regex.replaceSuspend(
    input: CharSequence,
    transform: suspend (MatchResult) -> CharSequence
): String {
    var match = find(input) ?: return input.toString()

    var lastStart = 0
    val length = input.length
    val sb = StringBuilder(length)
    do {
        val foundMatch = match
        sb.append(input, lastStart, foundMatch.range.first)
        sb.append(transform(foundMatch))
        lastStart = foundMatch.range.last + 1
        match = foundMatch.next() ?: break
    } while (lastStart < length)

    if (lastStart < length) {
        sb.append(input, lastStart, length)
    }

    return sb.toString()
}