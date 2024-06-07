package com.codepunk.skeleton.data.remote.entity

import kotlinx.serialization.Serializable

@Serializable
abstract class BaseRelease : Entity() {
    abstract val country: String
    abstract val year: String
    abstract val format: List<String>
    abstract val label: List<String>
    abstract val genre: List<String>
    abstract val style: List<String>
    abstract val barcode: List<String>
    abstract val categoryNumber: String
}