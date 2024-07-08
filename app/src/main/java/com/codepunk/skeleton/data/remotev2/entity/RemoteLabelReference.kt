package com.codepunk.skeleton.data.remotev2.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteLabelReference(
    override val id: Long = 0L,
    override val name: String = "",
    @SerialName("resource_url")
    override val resourceUrl: String = ""
) : RemoteReference
