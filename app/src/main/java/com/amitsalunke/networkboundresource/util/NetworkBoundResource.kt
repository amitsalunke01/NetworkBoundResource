package com.amitsalunke.networkboundresource.util

import kotlinx.coroutines.flow.*

//generic type arguments
inline fun <ResultType, RequestType> networkBoundResource(
    //query fetching data from db and fetch to get data from remote , saveFetchResult getting the data from remote and storing in DB
    //shouldFetch decides the data from the db is old so to fetch new data from web or not, if its true then update the cache
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow {
    val data =
        query().first()//collecting from flow but only one time and one value
    val flow = if (shouldFetch(data)) {
        emit(Resource.Loading(data))//show existing data while collect data from remote
        try {
            saveFetchResult(fetch())
            query().map { Resource.Success(it) }
        } catch (throwable: Throwable) {
            query().map { Resource.Error(throwable, it) }
        }
    } else {
        query().map { Resource.Success(it) }
    }
    emitAll(flow)
}