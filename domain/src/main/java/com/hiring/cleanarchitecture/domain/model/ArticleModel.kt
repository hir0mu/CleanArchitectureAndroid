package com.hiring.cleanarchitecture.domain.model

data class ArticleModel(
    val id: String,
    val title: String,
    val url: String,
    val user: UserModel,
    val isFavorite: Boolean
)

fun MutableList<ArticleModel>.merge(newItems: List<ArticleModel>) {
    val ids = this.map { it.id }
    newItems.forEach { new ->
        if (!ids.contains(new.id)) {
            this.add(new)
        }
    }
}
