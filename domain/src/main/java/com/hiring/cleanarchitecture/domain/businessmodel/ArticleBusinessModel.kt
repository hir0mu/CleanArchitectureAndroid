package com.hiring.cleanarchitecture.domain.businessmodel

import com.hiring.cleanarchitecture.domain.model.ArticleModel

data class ArticleBusinessModel(
    val article: ArticleModel,
    val isFavorite: Boolean
) : BusinessModel {
    companion object {
        val EMPTY = ArticleBusinessModel(
            article = ArticleModel.EMPTY,
            isFavorite = false
        )
    }
}
