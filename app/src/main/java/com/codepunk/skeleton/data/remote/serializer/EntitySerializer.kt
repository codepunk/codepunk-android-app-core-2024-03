package com.codepunk.skeleton.data.remote.serializer

import com.codepunk.skeleton.data.remote.entity.Artist
import com.codepunk.skeleton.data.remote.entity.Entity
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import com.codepunk.skeleton.data.remote.entity.Entity.Type.*
import com.codepunk.skeleton.data.remote.entity.Label
import com.codepunk.skeleton.data.remote.entity.Master
import com.codepunk.skeleton.data.remote.entity.Release
import com.codepunk.skeleton.data.remote.entity.UnknownEntity

object EntitySerializer : JsonContentPolymorphicSerializer<Entity>(Entity::class) {

    // region Methods

    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<Entity> {
        val json = element.jsonObject
        val typeValue = json.getValue("type").jsonPrimitive.content
        return when (Entity.Type.fromValue(typeValue)) {
            ARTIST -> Artist.serializer()
            LABEL -> Label.serializer()
            MASTER -> Master.serializer()
            RELEASE -> Release.serializer()
            else -> UnknownEntity.serializer()
        }
    }

    // endregion Methods

}