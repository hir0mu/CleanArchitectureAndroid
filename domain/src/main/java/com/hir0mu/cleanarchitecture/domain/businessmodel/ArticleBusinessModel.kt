package com.hir0mu.cleanarchitecture.domain.businessmodel

import com.hir0mu.cleanarchitecture.domain.model.ArticleModel

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
