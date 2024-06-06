package com.codepunk.skeleton.util

import arrow.core.Ior
import arrow.core.rightIor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

inline fun <Domain, Remote> networkBoundResource(
    crossinline query: () -> Flow<Domain>,
    crossinline fetch: suspend () -> Remote,
    crossinline saveFetchResult: suspend (Remote) -> Unit,
    crossinline onLoading: (Domain?) -> Unit = {},
    crossinline onFetchFailed: (Throwable) -> Unit = {},
    crossinline shouldFetch: (Domain) -> Boolean = { true },
): Flow<Ior<Throwable, Domain>> = flow {
    onLoading(null)
    val data = query().first()

    val flow = if (shouldFetch(data)) {
        onLoading(data)
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