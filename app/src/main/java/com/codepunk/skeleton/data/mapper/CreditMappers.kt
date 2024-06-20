package com.codepunk.skeleton.data.mapper

import com.codepunk.skeleton.data.local.entity.LocalCredit
import com.codepunk.skeleton.data.remote.entity.RemoteCredit
import com.codepunk.skeleton.domain.model.Credit

fun RemoteCredit.toLocalCredit(id: Long = 0): LocalCredit = LocalCredit(
    id = id,
    artistId = this.artistId,
    name = this.name,
    anv = this.anv,
    join = this.join,
    role = this.role,
    tracks = this.tracks,
    resourceUrl = this.resourceUrl,
    thumbnailUrl = this.thumbnailUrl
)

fun LocalCredit.toDomainCredit(): Credit = Credit(
    artistId = this.artistId,
    name = this.name,
    anv = this.anv,
    join = this.join,
    role = this.role,
    tracks = this.tracks,
    resourceUrl = this.resourceUrl,
    thumbnailUrl = this.thumbnailUrl

)
