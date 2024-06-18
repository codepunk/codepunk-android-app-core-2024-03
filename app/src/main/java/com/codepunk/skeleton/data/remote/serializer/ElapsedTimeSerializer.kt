package com.codepunk.skeleton.data.remote.serializer

import com.codepunk.skeleton.util.fromElapsedTimeString
import com.codepunk.skeleton.util.toElapsedTimeString
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlin.time.Duration
import kotlin.time.DurationUnit

class ElapsedTimeSerializer : KSerializer<Duration> {

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        serialName = "kotlin.time.Duration",
        kind = PrimitiveKind.STRING
    )

    override fun serialize(encoder: Encoder, value: Duration) {
        encoder.encodeString(value.toElapsedTimeString(DurationUnit.MINUTES))
    }

    override fun deserialize(decoder: Decoder): Duration = try {
        fromElapsedTimeString(decoder.decodeString())
    } catch (e: Exception) {
        Duration.ZERO
    }

}