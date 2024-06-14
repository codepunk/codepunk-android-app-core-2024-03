package com.codepunk.skeleton.data.remote.serializer

import com.codepunk.skeleton.domain.type.ImageType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object ImageTypeSerializer : KSerializer<ImageType> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor(serialName = "ImageType", kind = PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: ImageType) {
        encoder.encodeString(value.name.lowercase())
    }

    override fun deserialize(decoder: Decoder): ImageType = try {
        ImageType.valueOf(decoder.decodeString().uppercase())
    } catch (e: IllegalArgumentException) {
        ImageType.PRIMARY
    }

}