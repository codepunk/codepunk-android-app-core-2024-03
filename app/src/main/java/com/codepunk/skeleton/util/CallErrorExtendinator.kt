package com.codepunk.skeleton.util

import arrow.retrofit.adapter.either.networkhandling.CallError
import arrow.retrofit.adapter.either.networkhandling.HttpError
import arrow.retrofit.adapter.either.networkhandling.IOError
import arrow.retrofit.adapter.either.networkhandling.UnexpectedCallError
import com.codepunk.skeleton.util.http.HttpStatus
import com.codepunk.skeleton.util.http.HttpStatusException

fun CallError.toThrowable(): Throwable =
    when (this) {
        is HttpError -> HttpStatusException(
            HttpStatus.lookup(
                code = code,
                reasonPhrase = message
            )
        )
        is IOError -> cause
        is UnexpectedCallError -> cause
    }.apply {
        val x = "$this"
    }