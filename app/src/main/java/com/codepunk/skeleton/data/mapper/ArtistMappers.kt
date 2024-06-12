package com.codepunk.skeleton.data.mapper

import com.codepunk.skeleton.data.domain.Artist
import com.codepunk.skeleton.data.domain.Entity.UserData
import com.codepunk.skeleton.data.local.entity.LocalArtist
import com.codepunk.skeleton.data.remote.entity.RemoteArtist

fun RemoteArtist.toLocal(): LocalArtist = LocalArtist(
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
)

fun LocalArtist.toDomain(): Artist = Artist(
    id = this.id,
    title = this.title,
    userData = UserData(
        inWantList = this.inWantList,
        inCollection = this.inCollection,
    ),
    masterId = this.masterId,
    masterUrl = this.masterUrl,
    uri = this.uri,
    thumb = this.thumb,
    coverImage = this.coverImage,
    resourceUrl = this.resourceUrl
)

fun Artist.toLocal(): LocalArtist = LocalArtist(
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
)
