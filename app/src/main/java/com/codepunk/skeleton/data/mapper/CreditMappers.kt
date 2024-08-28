package com.codepunk.skeleton.data.mapper

import com.codepunk.skeleton.data.local.entity.LocalCredit
import com.codepunk.skeleton.data.local.entity.LocalCredit.CreditType
import com.codepunk.skeleton.data.remote.entity.RemoteCredit
import com.codepunk.skeleton.domain.model.Credit

fun RemoteCredit.toLocal(
    creditId: Long = 0L,
    type: CreditType
): LocalCredit =
    LocalCredit(
        creditId = creditId,
        type = type,
        artistId = artistId,
        name = name,
        anv = anv,
        join = join,
        role = role,
        tracks = tracks,
        resourceUrl = resourceUrl,
        thumbnailUrl = thumbnailUrl

    )

fun LocalCredit.toDomain(): Credit =
    Credit(
        artistId = artistId,
        name = name,
        resourceUrl = resourceUrl,
        anv = anv,
        join = join,
        role = role,
        tracks = tracks,
        thumbnailUrl = thumbnailUrl
    )
