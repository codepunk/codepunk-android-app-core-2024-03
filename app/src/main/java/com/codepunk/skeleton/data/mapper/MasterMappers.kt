package com.codepunk.skeleton.data.mapper

import com.codepunk.skeleton.data.domain.Master
import com.codepunk.skeleton.data.domain.Entity.UserData
import com.codepunk.skeleton.data.local.entity.LocalMaster
import com.codepunk.skeleton.data.local.relation.LocalMasterWithDetails
import com.codepunk.skeleton.data.remote.entity.RemoteMaster

fun RemoteMaster.toLocal(): LocalMasterWithDetails = LocalMasterWithDetails(
    master = LocalMaster(
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
    barcode = this.barcode.toList()
)

fun LocalMasterWithDetails.toDomain(): Master = Master(
    id = this.master.id,
    title = this.master.title,
    userData = UserData(
        inWantList = this.master.inWantList,
        inCollection = this.master.inCollection,
    ),
    masterId = this.master.masterId,
    masterUrl = this.master.masterUrl,
    uri = this.master.uri,
    thumb = this.master.thumb,
    coverImage = this.master.coverImage,
    resourceUrl = this.master.resourceUrl,
    format = this.format.toList(),
    label = this.label.toList(),
    genre = this.genre.toList(),
    style = this.style.toList(),
    barcode = this.barcode.toList()
)

fun Master.toLocal(): LocalMasterWithDetails = LocalMasterWithDetails(
    master = LocalMaster(
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
    barcode = this.barcode.toList()
)
