package com.hiring.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.hiring.data.db.ArticleGroupConverters
import com.hiring.data.db.ArticleTagConverters
import com.hiring.data.db.UserConverters

@Entity(tableName = "fav_article")
@TypeConverters(ArticleGroupConverters::class, ArticleTagConverters::class, UserConverters::class)
data class FavArticle(
    @ColumnInfo(name = "rendered_body")
    var renderedBody: String = "",

    @ColumnInfo(name = "body")
    var body: String = "",

    @ColumnInfo(name = "coediting")
    var coediting: Boolean = false,

    @ColumnInfo(name = "comments_count")
    var commentsCount: Int = 0,

    @ColumnInfo(name = "created_at")
    var createdAt: String = "",

    @ColumnInfo(name = "group")
    var group: ArticleGroup? = null,

    @PrimaryKey
    var id: String = "",

    @ColumnInfo(name = "likes_count")
    var likesCount: Int = 0,

    @ColumnInfo(name = "private")
    var private: Boolean = false,

    @ColumnInfo(name = "reactions_count")
    var reactionsCount: Int = 0,

    @ColumnInfo(name = "tags")
    var tags: ArticleTag? = null,

    @ColumnInfo(name = "title")
    var title: String = "",

    @ColumnInfo(name = "updated_at")
    var updatedAt: String = "",

    @ColumnInfo(name = "url")
    var url: String = "",

    @ColumnInfo(name = "user")
    var user: User? = null,

    @ColumnInfo(name = "page_views_count")
    var pageViewsCount: Int = 0
)
