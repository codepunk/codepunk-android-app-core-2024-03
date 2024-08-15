package com.codepunk.skeleton.data.local.typeconverter

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDate
import java.time.format.DateTimeParseException

class LocalDateTypeConverter {

    @TypeConverter
    fun localDateToString(input: LocalDate?): String = input?.toString() ?: ""

    @TypeConverter
    fun stringToLocalDate(input: String?): LocalDate =
        input?.let {
            try {
                LocalDate.parse(it)
            } catch (e: DateTimeParseException) {
                LocalDate.fromEpochDays(0)
            }
        } ?: LocalDate.fromEpochDays(0)

}