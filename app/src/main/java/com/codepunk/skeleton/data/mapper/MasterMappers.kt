package com.codepunk.skeleton.data.mapper

import com.codepunk.skeleton.data.local.entity.LocalCredit
import com.codepunk.skeleton.data.local.entity.LocalCredit.CreditType
import com.codepunk.skeleton.data.local.entity.LocalMaster
import com.codepunk.skeleton.data.local.entity.LocalResourceDetail
import com.codepunk.skeleton.data.local.entity.LocalResourceDetail.DetailType
import com.codepunk.skeleton.data.local.relation.LocalMasterWithDetails
import com.codepunk.skeleton.data.local.relation.LocalResourceAndMaster
import com.codepunk.skeleton.data.remote.entity.RemoteMaster
import com.codepunk.skeleton.domain.model.Master

fun RemoteMaster.toLocalMaster(
    resourceId: Long
): LocalMaster =
    LocalMaster(
        masterId = id,
        resourceId = resourceId,
        title = title,
        year = year,
        numForSale = numForSale,
        lowestPrice = lowestPrice,
        mainRelease = mainRelease,
        mostRecentRelease = mostRecentRelease,
        versionsUrl = versionsUrl,
        mainReleaseUrl = mainReleaseUrl,
        mostRecentReleaseUrl = mostRecentReleaseUrl
    )

fun RemoteMaster.toLocal(
    resourceId: Long = 0L
): LocalResourceAndMaster =
    LocalResourceAndMaster(
        resource = toLocalResource(resourceId),
        masterWithDetails = LocalMasterWithDetails(
            master = toLocalMaster(resourceId),
            images = images.mapIndexed { imageIdx, image ->
                image.toLocal(imageIdx = imageIdx)
            },
            details = genres.mapIndexed { detailIdx, url ->
                LocalResourceDetail(resourceId, DetailType.GENRE, detailIdx, url)
            } + styles.mapIndexed { detailIdx, name ->
                LocalResourceDetail(resourceId, DetailType.STYLE, detailIdx, name)
            },
            trackList = trackList.toLocal(),
            credits = artists.map { it.toLocal(type = CreditType.ARTIST) },
            videos = videos.map { it.toLocal() }
        )
    )

fun LocalResourceAndMaster.toDomain(): Master =
    Master(
        id = masterWithDetails.master.masterId,
        resourceUrl = resource.resourceUrl,
        uri = resource.uri,
        images = masterWithDetails.images.map { it.toDomain() },
        dataQuality = resource.dataQuality,
        title = masterWithDetails.master.title,
        genres = masterWithDetails.details.filter {
            it.type == DetailType.GENRE
        }.map { it.detail },
        styles = masterWithDetails.details.filter {
            it.type == DetailType.STYLE
        }.map { it.detail },
        year = masterWithDetails.master.year,
        numForSale = masterWithDetails.master.numForSale,
        lowestPrice = masterWithDetails.master.lowestPrice,
        trackList = masterWithDetails.trackList.toDomain(),
        artists = masterWithDetails.credits.filter {
            it.type == CreditType.ARTIST
        }.map { it.toDomain() },
        videos = masterWithDetails.videos.map { it.toDomain() },
        mainRelease = masterWithDetails.master.mainRelease,
        mostRecentRelease = masterWithDetails.master.mostRecentRelease,
        versionsUrl = masterWithDetails.master.versionsUrl,
        mainReleaseUrl = masterWithDetails.master.mainReleaseUrl,
        mostRecentReleaseUrl = masterWithDetails.master.mostRecentReleaseUrl
    )
