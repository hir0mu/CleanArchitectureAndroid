package com.hiring.cleanarchitecture.domain.model

data class ArticleModel(
    val id: String,
    val title: String,
    val url: String,
    val user: UserModel,
    val isFavorite: Boolean
)

fun MutableList<ArticleModel>.merge(newItems: List<ArticleModel>) {
    newItems.forEach { new ->
        if (any { it.id != new.id }) {
            add(new)
        }
    }
}
