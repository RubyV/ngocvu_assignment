package com.ngocvu.example.utils

import com.bumptech.glide.load.engine.Resource
import kotlinx.coroutines.flow.*

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow {
    val data = query().first()

    val flow = if (shouldFetch(data)) {
        emit(com.ngocvu.example.utils.Resource.Loading(data))

        try {
            saveFetchResult(fetch())
            query().map { com.ngocvu.example.utils.Resource.Success(it) }
        } catch (throwable: Throwable) {
            query().map { com.ngocvu.example.utils.Resource.Error(throwable, it) }
        }
    } else {
        query().map { com.ngocvu.example.utils.Resource.Success(it) }
    }

    emitAll(flow)
}