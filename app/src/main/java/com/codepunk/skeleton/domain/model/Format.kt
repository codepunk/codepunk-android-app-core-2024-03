package com.codepunk.skeleton.domain.model

data class Format(
    val name: String = "",
    val quantity: Int = 0,
    val text: String? = null,
    val descriptions: List<String> = emptyList()
)
