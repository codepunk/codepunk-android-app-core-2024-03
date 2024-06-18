package com.codepunk.skeleton.data.remote.serializer

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.JsonUnquotedLiteral
import kotlinx.serialization.json.jsonPrimitive
import java.math.BigDecimal

@OptIn(ExperimentalSerializationApi::class)
class BigDecimalSerializer : KSerializer<BigDecimal> {

    // region Methods

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        serialName = "java.math.BigDecimal",
        kind = PrimitiveKind.DOUBLE
    )

    override fun serialize(encoder: Encoder, value: BigDecimal) {
        when (encoder) {
            is JsonEncoder -> encoder.encodeJsonElement(
                JsonUnquotedLiteral(value.toPlainString())
            )
            else -> encoder.encodeString(value.toPlainString())
        }
    }

    override fun deserialize(decoder: Decoder): BigDecimal = when (decoder) {
        is JsonDecoder -> decoder.decodeJsonElement().jsonPrimitive.content.toBigDecimal()
        else -> decoder.decodeString().toBigDecimal()
    }

    // endregion Methods

}