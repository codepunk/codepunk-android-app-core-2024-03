package com.codepunk.skeleton.domainv2.model

data class LabelReference(
    override val id: Long = 0L,
    override val name: String = "",
    override val resourceUrl: String = ""
) : Reference
