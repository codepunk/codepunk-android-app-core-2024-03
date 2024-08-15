package com.codepunk.skeleton.data.remote.serializer

import kotlinx.datetime.LocalDate
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.lang.RuntimeException
import java.time.Month

class LocalDateSerializer : KSerializer<LocalDate> {

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        serialName = "kotlinx.datetime.LocalDate",
        kind = PrimitiveKind.STRING
    )

    override fun serialize(encoder: Encoder, value: LocalDate) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): LocalDate {
        val str = decoder.decodeString()
        return try {
            LocalDate.parse(str)
        } catch (e: RuntimeException) {
            str.toIntOrNull()?.let { year ->
                LocalDate(year, Month.JANUARY, 1)
            } ?: LocalDate.fromEpochDays(0)
        }
    }
}