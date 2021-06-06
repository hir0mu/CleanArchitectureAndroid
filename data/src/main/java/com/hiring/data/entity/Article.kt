package com.hiring.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Article(
    @SerialName("rendered_body")
    val renderedBody: String,
    @SerialName("body")
    val body: String,
    @SerialName("coediting")
    val coediting: Boolean,
    @SerialName("comments_count")
    val commentsCount: Int,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("group")
    val group: ArticleGroup?,
    @SerialName("id")
    val id: String,
    @SerialName("likes_count")
    val likesCount: Int,
    @SerialName("private")
    val private: Boolean,
    @SerialName("reactions_count")
    val reactionsCount: Int,
    @SerialName("tags")
    val tags: List<ArticleTag>,
    @SerialName("title")
    val title: String,
    @SerialName("updated_at")
    val updatedAt: String,
    @SerialName("url")
    val url: String,
    @SerialName("user")
    val user: User,
    @SerialName("page_views_count")
    val pageViewsCount: Int?
)
