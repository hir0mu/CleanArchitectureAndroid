package com.hiring.data.db

import androidx.room.TypeConverter
import com.hiring.data.entity.ArticleGroup
import com.hiring.data.entity.ArticleTag
import com.hiring.data.entity.User
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class UserConverters {
    @TypeConverter
    fun toUser(json: String): User = Json.decodeFromString(json)

    @TypeConverter
    fun toString(user: User): String = Json.encodeToString(user)
}

class ArticleGroupConverters {
    @TypeConverter
    fun toArticleGroup(json: String): ArticleGroup = Json.decodeFromString(json)

    @TypeConverter
    fun toString(articleGroup: ArticleGroup): String = Json.encodeToString(articleGroup)
}

class ArticleTagConverters {
    @TypeConverter
    fun toUser(json: String): ArticleTag = Json.decodeFromString(json)

    @TypeConverter
    fun toString(articleTag: ArticleTag): String = Json.encodeToString(articleTag)
}
