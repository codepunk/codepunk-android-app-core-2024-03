package com.codepunk.skeleton.data.remote.webservice

import arrow.core.Either
import arrow.retrofit.adapter.either.networkhandling.CallError
import com.codepunk.skeleton.data.remote.entity.RemoteArtist
import com.codepunk.skeleton.data.remote.entity.RemoteLabel
import com.codepunk.skeleton.data.remote.entity.RemoteMaster
import com.codepunk.skeleton.data.remote.entity.RemoteRelease
import com.codepunk.skeleton.data.remote.entity.RemoteReleasesByArtist
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DiscogsWebservice {

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

    @GET("artists/{artistId}/releases")
    suspend fun getReleasesByArtist(
        @Path("artistId") artistId: Long,
        @Query("sort") sort: String,
        @Query("sort_order") sortOrder: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): Either<CallError, RemoteReleasesByArtist>

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