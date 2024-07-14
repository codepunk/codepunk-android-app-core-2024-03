package com.codepunk.skeleton.data.mapper

import com.codepunk.skeleton.data.local.entity.LocalImage
import com.codepunk.skeleton.data.remote.entity.RemoteImage
import com.codepunk.skeleton.domain.model.Image

fun RemoteImage.toLocal(
    imageId: Long = 0L,
    imageIdx: Int
): LocalImage =
    LocalImage(
        imageId = imageId,
        imageIdx = imageIdx,
        type = type,
        uri = uri,
        resourceUrl = resourceUrl,
        uri150 = uri150,
        width = width,
        height = height
    )

fun LocalImage.toDomain(): Image =
    Image(
        type = type,
        uri = uri,
        resourceUrl = resourceUrl,
        uri150 = uri150,
        width = width,
        height = height
    )
