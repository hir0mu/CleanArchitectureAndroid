package com.hir0mu.cleanarchitecture.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArticleTagEntity(
    @SerialName("name")
    val name: String,
    @SerialName("versions")
    val versions: List<String>
)
