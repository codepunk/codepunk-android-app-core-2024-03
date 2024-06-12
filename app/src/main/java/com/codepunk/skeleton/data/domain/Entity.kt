package com.codepunk.skeleton.data.domain

import androidx.room.ColumnInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed class Entity {

    // region Variables

    open val id: Long = 0
    open val title: String = ""
    open val userData: UserData = UserData()
    open val masterId: Int? = null
    open val masterUrl: String? = null
    open val uri: String = ""
    open val thumb: String = ""
    open val coverImage: String = ""
    open val resourceUrl: String = ""

    // endregion Variables

    // region Nested & inner classes

    data class UserData(
        val inWantList: Boolean = false,
        val inCollection: Boolean = false
    )

    // endregion Nested & inner classes

}