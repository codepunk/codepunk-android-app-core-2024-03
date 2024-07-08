package com.codepunk.skeleton.domainv2.model

data class CreditReference(
    override val id: Long = 0L,
    override val name: String = "",
    override val resourceUrl: String = "",
    val anv: String = "",
    val join: String = "",
    val role: String = "",
    val tracks: String = ""
) : Reference
