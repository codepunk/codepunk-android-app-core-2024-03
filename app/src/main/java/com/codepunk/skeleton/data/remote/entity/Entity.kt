package com.codepunk.skeleton.data.remote.entity

import com.codepunk.skeleton.data.remote.serializer.EntitySerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(with = EntitySerializer::class)
sealed class Entity {

    // region Variables

    abstract val id: Int
    abstract val title: String
    abstract val userData: UserData
    abstract val masterId: Int?
    abstract val masterUrl: String?
    abstract val uri: String
    abstract val thumb: String
    abstract val coverImage: String
    abstract val resourceUrl: String

    // endregion Variables

    // region Nested & inner classes

    @Serializable
    data class UserData(
        @SerialName(value = "in_wantlist")
        val inWantList: Boolean,
        @SerialName(value = "in_collection")
        val inCollection: Boolean
    )

    enum class Type(val value: String) {
        ARTIST("artist"),
        LABEL("label"),
        MASTER("master"),
        RELEASE("release"),
        UNKNOWN("unknown");

        companion object {
            fun fromValue(value: String, default: Type = UNKNOWN): Type =
                entries.find {
                    it.value == value
                } ?: default
        }
    }

    // endregion Nested & inner classes

}
