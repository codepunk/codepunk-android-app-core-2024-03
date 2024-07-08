package com.codepunk.skeleton.data.remote.webservice

import arrow.core.Either
import arrow.retrofit.adapter.either.networkhandling.CallError
import com.codepunk.skeleton.data.remotev2.entity.RemoteArtist
import com.codepunk.skeleton.data.remotev2.entity.RemoteLabel
import com.codepunk.skeleton.data.remotev2.entity.RemoteMaster
import com.codepunk.skeleton.data.remotev2.entity.RemoteRelease
import retrofit2.http.GET
import retrofit2.http.Path

interface DiscogsWebserviceV2 {

    // region Methods

    /* TODO
    @GET("database/search")
    suspend fun search(
        @Query(value = "query")
        query: String
    ): Either<CallError, RemoteSearchResults>
     */

    @GET("artists/{artistId}")
    suspend fun getArtist(
        @Path("artistId") artistId: Long
    ): Either<CallError, RemoteArtist>

    @GET("labels/{labelId}")
    suspend fun getLabel(
        @Path("labelId") labelId: Long
    ): Either<CallError, RemoteLabel>

    @GET("masters/{masterId}")
    suspend fun getMaster(
        @Path("masterId") masterId: Long
    ): Either<CallError, RemoteMaster>

    @GET("releases/{releaseId}")
    suspend fun getRelease(
        @Path("releaseId") releaseId: Long
    ): Either<CallError, RemoteRelease>

    // endregion Methods

}