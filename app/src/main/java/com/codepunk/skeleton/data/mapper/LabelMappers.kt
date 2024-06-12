package com.codepunk.skeleton.data.mapper

import com.codepunk.skeleton.data.domain.Label
import com.codepunk.skeleton.data.domain.Entity.UserData
import com.codepunk.skeleton.data.local.entity.LocalLabel
import com.codepunk.skeleton.data.remote.entity.RemoteLabel

fun RemoteLabel.toLocal(): LocalLabel = LocalLabel(
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

fun LocalLabel.toDomain(): Label = Label(
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

fun Label.toLocal(): LocalLabel = LocalLabel(
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
