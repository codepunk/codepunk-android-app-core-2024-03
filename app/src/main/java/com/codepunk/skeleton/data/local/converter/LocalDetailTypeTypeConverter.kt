package com.codepunk.skeleton.data.local.converter

import androidx.room.TypeConverter
import com.codepunk.skeleton.data.local.entity.LocalReleaseDetail.DetailType

class LocalDetailTypeTypeConverter {

    // region Methods

    @TypeConverter
    fun toDetailType(value: String): DetailType =
        DetailType.valueOf(value.uppercase())

    @TypeConverter
    fun toString(type: DetailType): String =
        type.name.lowercase()

    // endregion Methods

}
