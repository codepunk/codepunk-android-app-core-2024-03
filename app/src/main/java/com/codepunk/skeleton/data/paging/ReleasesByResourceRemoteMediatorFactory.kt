package com.codepunk.skeleton.data.paging

import com.codepunk.skeleton.data.local.DiscogsDatabase
import com.codepunk.skeleton.data.remote.webservice.DiscogsWebservice
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReleasesByResourceRemoteMediatorFactory @Inject constructor(
    private val webservice: DiscogsWebservice,
    private val database: DiscogsDatabase
) {

    // region Methods

    fun create(
        artistId: Long,
        pageSize: Int,
        sort: String,
        ascending: Boolean
    ): ReleasesByResourceRemoteMediator = ReleasesByResourceRemoteMediator(
        artistId = artistId,
        pageSize = pageSize,
        sort = sort,
        ascending = ascending,
        webservice = webservice,
        database = database
    )

    // endregion Methods

}