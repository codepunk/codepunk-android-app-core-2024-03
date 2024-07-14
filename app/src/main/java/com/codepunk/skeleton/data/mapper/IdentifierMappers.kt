package com.codepunk.skeleton.data.mapper

import com.codepunk.skeleton.data.local.entity.LocalIdentifier
import com.codepunk.skeleton.data.remote.entity.RemoteIdentifier
import com.codepunk.skeleton.domain.model.Identifier

fun RemoteIdentifier.toLocal(
    releaseId: Long = 0L,
    identifierIdx: Int
): LocalIdentifier =
    LocalIdentifier(
        releaseId = releaseId,
        identifierIdx = identifierIdx,
        type = type,
        value = value,
        description = description
    )

fun LocalIdentifier.toDomain(): Identifier =
    Identifier(
        type = type,
        value = value,
        description = description
    )
