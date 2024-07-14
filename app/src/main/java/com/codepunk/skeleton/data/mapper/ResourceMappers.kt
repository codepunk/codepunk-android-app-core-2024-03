package com.codepunk.skeleton.data.mapper

import com.codepunk.skeleton.data.local.entity.LocalResource
import com.codepunk.skeleton.data.remote.entity.RemoteResource

fun RemoteResource.toLocalResource(
    resourceId: Long = 0L
): LocalResource =
    LocalResource(
        resourceId = resourceId,
        resourceUrl = resourceUrl,
        uri = uri,
        dataQuality = dataQuality
    )
