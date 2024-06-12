package com.codepunk.skeleton.data.remote.serializer

import com.codepunk.skeleton.data.remote.entity.RemoteArtist
import com.codepunk.skeleton.data.remote.entity.RemoteEntity
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import com.codepunk.skeleton.data.remote.entity.RemoteEntity.Type.*
import com.codepunk.skeleton.data.remote.entity.RemoteLabel
import com.codepunk.skeleton.data.remote.entity.RemoteMaster
import com.codepunk.skeleton.data.remote.entity.RemoteRelease
import com.codepunk.skeleton.data.remote.entity.RemoteUnknownEntity

object EntitySerializer : JsonContentPolymorphicSerializer<RemoteEntity>(RemoteEntity::class) {

    // region Methods

    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<RemoteEntity> {
        val json = element.jsonObject
        val typeValue = json.getValue("type").jsonPrimitive.content
        return when (RemoteEntity.Type.fromValue(typeValue)) {
            ARTIST -> RemoteArtist.serializer()
            LABEL -> RemoteLabel.serializer()
            MASTER -> RemoteMaster.serializer()
            RELEASE -> RemoteRelease.serializer()
            else -> RemoteUnknownEntity.serializer()
        }
    }

    // endregion Methods

}