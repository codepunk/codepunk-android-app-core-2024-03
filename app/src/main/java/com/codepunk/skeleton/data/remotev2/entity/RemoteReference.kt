package com.codepunk.skeleton.data.remotev2.entity

sealed interface RemoteReference {
    val id: Long
    val name: String
    val resourceUrl: String
}
