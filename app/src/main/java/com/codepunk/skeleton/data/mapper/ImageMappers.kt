package com.codepunk.skeleton.data.mapper

import com.codepunk.skeleton.data.local.entity.LocalImage
import com.codepunk.skeleton.data.remote.entity.RemoteImage
import com.codepunk.skeleton.domain.model.Image

fun RemoteImage.toLocalImage(id: Long = 0): LocalImage = LocalImage(
    id = id,
    type = this.type,
    uri = this.uri,
    resourceUrl = this.resourceUrl,
    uri150 = this.uri150,
    width = this.width,
    height = this.height
)

fun LocalImage.toDomainImage(): Image = Image(
    type = this.type,
    uri = this.uri,
    resourceUrl = this.resourceUrl,
    uri150 = this.uri150,
    width = this.width,
    height = this.height
)
