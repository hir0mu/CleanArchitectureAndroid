package com.hiring.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.hiring.data.db.UserConverters

@Entity(tableName = "fav_article")
@TypeConverters(UserConverters::class)
data class FavArticleEntity(
    @PrimaryKey
    var id: String = "",

    @ColumnInfo(name = "title")
    var title: String = "",

    @ColumnInfo(name = "url")
    var url: String = "",

    @ColumnInfo(name = "user")
    var user: UserEntity? = null,

    @ColumnInfo(name = "created_at")
    var createdAt: Long = System.currentTimeMillis()
)
