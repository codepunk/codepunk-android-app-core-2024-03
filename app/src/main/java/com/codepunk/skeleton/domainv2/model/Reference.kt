package com.codepunk.skeleton.domainv2.model

sealed interface Reference {
    val id: Long
    val name: String
    val resourceUrl: String
}
