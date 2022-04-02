package com.hiring.data.db

import androidx.room.TypeConverter
import com.hiring.data.entity.UserEntity
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@ExperimentalSerializationApi
class UserConverters {
    @TypeConverter
    fun toUser(json: String): UserEntity = Json.decodeFromString(json)

    @TypeConverter
    fun toString(user: UserEntity): String = Json.encodeToString(user)
}
