package com.codepunk.skeleton.data.remote.webservice

import arrow.core.Either
import arrow.retrofit.adapter.either.networkhandling.CallError
import com.codepunk.skeleton.data.remote.entity.SearchResults
import retrofit2.http.GET
import retrofit2.http.Query

interface DiscogsWebService {

    // region Methods

    @GET("database/search")
    suspend fun search(
        @Query(value = "query")
        query: String
    ): Either<CallError, SearchResults>

    // endregion Methods

}