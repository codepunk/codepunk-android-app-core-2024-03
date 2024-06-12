package com.codepunk.skeleton.data.domain

data class Artist(
    override val id: Long,
    override val title: String = "",
    override val userData: UserData = UserData(),
    override val masterId: Int? = null,
    override val masterUrl: String? = null,
    override val uri: String = "",
    override val thumb: String = "",
    override val coverImage: String = "",
    override val resourceUrl: String = ""
) : Entity()
