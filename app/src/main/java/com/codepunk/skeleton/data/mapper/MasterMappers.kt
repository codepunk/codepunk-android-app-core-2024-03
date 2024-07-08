package com.codepunk.skeleton.data.mapper

import com.codepunk.skeleton.data.local.entity.LocalMaster
import com.codepunk.skeleton.data.local.entity.LocalMasterDetail
import com.codepunk.skeleton.data.local.relation.LocalMasterWithDetails
import com.codepunk.skeleton.data.local.type.ResourceDetailType
import com.codepunk.skeleton.data.remote.entity.RemoteMaster
import com.codepunk.skeleton.domain.model.Master

// region Methods

fun RemoteMaster.toLocalMasterWithDetails(): LocalMasterWithDetails = LocalMasterWithDetails(
    master = LocalMaster(
        id = this.id,
        title = this.title,
        mainRelease = this.mainRelease,
        mostRecentRelease = this.mostRecentRelease,
        resourceUrl = this.resourceUrl,
        uri = this.uri,
        versionsUrl = this.versionsUrl,
        mainReleaseUrl = this.mainReleaseUrl,
        mostRecentReleaseUrl = this.mostRecentReleaseUrl,
        numForSale = this.numForSale,
        lowestPrice = this.lowestPrice,
        year = this.year,
        dataQuality = this.dataQuality
    ),
    images = this.images.map { it.toLocalImage() },
    details = this.genres.toLocalMasterDetails(this.id, ResourceDetailType.GENRE) +
            this.styles.toLocalMasterDetails(this.id, ResourceDetailType.STYLE),
    trackList = this.trackList.map { it.toLocalTrack() },
    artists = this.artists.map { it.toLocalCredit() },
    videos = this.videos.map { it.toLocalVideo() }
)

private fun List<String>.toLocalMasterDetails(
    id: Long,
    detailType: ResourceDetailType
): List<LocalMasterDetail> = mapIndexed { detailIdx, detail ->
    LocalMasterDetail(
        artistId = id,
        detailType = detailType,
        detailIdx = detailIdx,
        detail = detail
    )
}

fun LocalMasterWithDetails.toDomainMaster(): Master = Master(
    id = master.id,
    title = master.title,
    mainRelease = master.mainRelease,
    mostRecentRelease = master.mostRecentRelease,
    resourceUrl = master.resourceUrl,
    uri = master.uri,
    versionsUrl = master.versionsUrl,
    mainReleaseUrl = master.mainReleaseUrl,
    mostRecentReleaseUrl = master.mostRecentReleaseUrl,
    numForSale = master.numForSale,
    lowestPrice = master.lowestPrice,
    images = images.map { it.toDomainImage() },
    genres = details.toDomainMasterDetails(ResourceDetailType.GENRE),
    styles = details.toDomainMasterDetails(ResourceDetailType.STYLE),
    year = master.year,
    trackList = trackList.map { it.toDomainTrack() },
    artists = artists.map { it.toDomainCredit() },
    dataQuality = master.dataQuality,
    videos = videos.map { it.toDomainVideo() }
)

private fun List<LocalMasterDetail>.toDomainMasterDetails(
    detailType: ResourceDetailType
): List<String> = filter { detail ->
    detail.detailType == detailType
}.map { it.detail }

// endregion Methods
