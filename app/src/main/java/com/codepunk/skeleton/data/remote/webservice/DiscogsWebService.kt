package com.codepunk.skeleton.data.remote.webservice

import arrow.core.Either
import arrow.retrofit.adapter.either.networkhandling.CallError
import com.codepunk.skeleton.data.remote.entity.RemoteArtist
import com.codepunk.skeleton.data.remote.entity.RemoteLabel
import retrofit2.http.GET
import retrofit2.http.Path

interface DiscogsWebService {

    // region Methods

    /* TODO
    @GET("database/search")
    suspend fun search(
        @Query(value = "query")
        query: String
    ): Either<CallError, RemoteSearchResults>
     */

    @GET("artists/{id}")
    suspend fun getArtist(
        @Path("id") id: Long
    ): Either<CallError, RemoteArtist>

    @GET("labels/{id}")
    suspend fun getLabel(
        @Path("id") id: Long
    ): Either<CallError, RemoteLabel>

    // endregion Methods

}