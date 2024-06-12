package com.codepunk.skeleton.data.domain

data class Release(
    override val id: Long,
    override val title: String = "",
    override val userData: UserData = UserData(),
    override val masterId: Int? = null,
    override val masterUrl: String? = null,
    override val uri: String = "",
    override val thumb: String = "",
    override val coverImage: String = "",
    override val resourceUrl: String = "",
    val format: List<String> = emptyList(),
    val label: List<String> = emptyList(),
    val genre: List<String> = emptyList(),
    val style: List<String> = emptyList(),
    val barcode: List<String> = emptyList(),
    val formats: List<Format> = emptyList()
) : Entity() {

    // region Nested & inner classes

    data class Format(
        val name: String = "",
        val quantity: Int = 0,
        val text: String = "",
        val descriptions: List<String> = emptyList()
    )

    // endregion Nested & inner classes

}
