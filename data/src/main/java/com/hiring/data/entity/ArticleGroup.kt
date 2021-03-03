package com.hiring.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArticleGroup(
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("private")
    val private: Boolean,
    @SerialName("updated_at")
    val updatedAt: String,
    @SerialName("url_name")
    val urlName: String,
)
