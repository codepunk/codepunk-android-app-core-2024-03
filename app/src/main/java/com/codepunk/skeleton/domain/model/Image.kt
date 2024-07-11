package com.codepunk.skeleton.domain.model

import com.codepunk.skeleton.domain.type.ImageType

data class Image(
    val type: ImageType = ImageType.PRIMARY,
    val uri: String = "",
    val resourceUrl: String = "",
    val uri150: String = "",
    val width: Int = 0,
    val height: Int = 0
)
