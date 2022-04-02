package com.hiring.cleanarchitecture.domain.model

data class ArticleModel(
    val id: String,
    val title: String,
    val url: String,
    val user: UserModel
) {
    companion object {
        val EMPTY: ArticleModel = ArticleModel(
            id = "",
            title = "",
            url = "",
            user = UserModel("", "", "")
        )
    }
}
