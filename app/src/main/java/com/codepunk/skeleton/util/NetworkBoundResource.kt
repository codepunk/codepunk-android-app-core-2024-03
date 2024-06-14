package com.codepunk.skeleton.util

import arrow.core.Ior
import arrow.core.rightIor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

inline fun <Domain, Remote> networkBoundResource(
    crossinline query: () -> Flow<Domain>,
    crossinline fetch: suspend () -> Remote,
    crossinline saveFetchResult: suspend (Remote) -> Unit,
    crossinline shouldFetch: (Domain?) -> Boolean = { true },
    crossinline onFetchFailed: (Throwable) -> Unit = {},
): Flow<Ior<Throwable, Domain?>> = flow {
    val data = query().firstOrNull()
    val flow = if (shouldFetch(data)) {
        emit(data.rightIor())
        try {
            saveFetchResult(fetch())
            query().map { it.rightIor() }
        } catch (throwable: Throwable) {
            onFetchFailed(throwable)
            query().map { Ior.Both(throwable, data) }
        }
    } else {
        query().map { it.rightIor() }
    }
    emitAll(flow)
}