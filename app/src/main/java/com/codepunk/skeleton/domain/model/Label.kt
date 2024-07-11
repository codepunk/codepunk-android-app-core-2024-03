package com.codepunk.skeleton.domain.model

data class Label(
    override val id: Long = 0L,
    override val resourceUrl: String = "",
    override val uri: String = "",
    override val images: List<Image> = emptyList(),
    override val dataQuality: String = "",
    override val name: String = "",
    override val profile: String = "",
    override val releasesUrl: String = "",
    override val urls: List<String> = emptyList(),
    val contactInfo: String = "",
    val parentLabel: LabelReference? = null,
    val subLabels: List<LabelReference> = emptyList()
) : Entity
