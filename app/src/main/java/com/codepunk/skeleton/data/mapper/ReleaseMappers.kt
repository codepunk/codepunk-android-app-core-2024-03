package com.codepunk.skeleton.data.mapper

import com.codepunk.skeleton.data.domain.Release
import com.codepunk.skeleton.data.domain.Entity.UserData
import com.codepunk.skeleton.data.local.entity.LocalRelease
import com.codepunk.skeleton.data.local.entity.LocalReleaseFormat
import com.codepunk.skeleton.data.local.entity.LocalReleaseFormatDescription
import com.codepunk.skeleton.data.local.relation.LocalReleaseFormatWithDescriptions
import com.codepunk.skeleton.data.local.relation.LocalReleaseWithDetails
import com.codepunk.skeleton.data.remote.entity.RemoteRelease

fun RemoteRelease.Format.toLocal(
    formatIdx: Int,
    releaseId: Long = 0L
): LocalReleaseFormatWithDescriptions = LocalReleaseFormatWithDescriptions(
    releaseFormat = LocalReleaseFormat(
        id = 0,
        formatIdx = formatIdx,
        releaseId = releaseId,
        name = this.name,
        quantity = this.quantity,
        text = this.text
    ),
    descriptions = this.descriptions.mapIndexed { index, description ->
        LocalReleaseFormatDescription(
            descriptionIdx = index,
            description = description
        )
    }
)

fun Release.Format.toLocal(
    formatIdx: Int,
    releaseId: Long = 0L
): LocalReleaseFormatWithDescriptions = LocalReleaseFormatWithDescriptions(
    releaseFormat = LocalReleaseFormat(
        id = 0,
        formatIdx = formatIdx,
        releaseId = releaseId,
        name = this.name,
        quantity = this.quantity,
        text = this.text
    ),
    descriptions = this.descriptions.mapIndexed { index, description ->
        LocalReleaseFormatDescription(
            descriptionIdx = index,
            description = description
        )
    }
)

fun LocalReleaseFormatWithDescriptions.toDomain(): Release.Format = Release.Format(
    name = releaseFormat.name,
    quantity = releaseFormat.quantity,
    text = releaseFormat.text,
    descriptions = descriptions.map {
        it.description
    }
)

fun RemoteRelease.toLocal(): LocalReleaseWithDetails = LocalReleaseWithDetails(
    release = LocalRelease(
        id = this.id,
        title = this.title,
        inWantList = this.userData.inWantList,
        inCollection = this.userData.inCollection,
        masterId = this.masterId,
        masterUrl = this.masterUrl,
        uri = this.uri,
        thumb = this.thumb,
        coverImage = this.coverImage,
        resourceUrl = this.resourceUrl
    ),
    format = this.format.toList(),
    label = this.label.toList(),
    genre = this.genre.toList(),
    style = this.style.toList(),
    barcode = this.barcode.toList(),
    formats = this.formats.mapIndexed { index, format ->
        format.toLocal(formatIdx = index)
    }
)

fun LocalReleaseWithDetails.toDomain(): Release = Release(
    id = this.release.id,
    title = this.release.title,
    userData = UserData(
        inWantList = this.release.inWantList,
        inCollection = this.release.inCollection,
    ),
    masterId = this.release.masterId,
    masterUrl = this.release.masterUrl,
    uri = this.release.uri,
    thumb = this.release.thumb,
    coverImage = this.release.coverImage,
    resourceUrl = this.release.resourceUrl,
    format = this.format.toList(),
    label = this.label.toList(),
    genre = this.genre.toList(),
    style = this.style.toList(),
    barcode = this.barcode.toList(),
    formats = this.formats.map { it.toDomain() }
)

fun Release.toLocal(): LocalReleaseWithDetails = LocalReleaseWithDetails(
    release = LocalRelease(
        id = this.id,
        title = this.title,
        inWantList = this.userData.inWantList,
        inCollection = this.userData.inCollection,
        masterId = this.masterId,
        masterUrl = this.masterUrl,
        uri = this.uri,
        thumb = this.thumb,
        coverImage = this.coverImage,
        resourceUrl = this.resourceUrl,
    ),
    format = this.format.toList(),
    label = this.label.toList(),
    genre = this.genre.toList(),
    style = this.style.toList(),
    barcode = this.barcode.toList(),
    formats = this.formats.mapIndexed { index, format ->
        format.toLocal(formatIdx = index)
    }
)
