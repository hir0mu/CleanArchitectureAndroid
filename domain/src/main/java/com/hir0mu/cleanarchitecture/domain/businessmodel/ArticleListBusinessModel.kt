package com.hir0mu.cleanarchitecture.domain.businessmodel

data class ArticleListBusinessModel(
    val articles: List<ArticleBusinessModel>
) : BusinessModel {
    companion object {
        val EMPTY = ArticleListBusinessModel(
            articles = listOf()
        )
    }
}
