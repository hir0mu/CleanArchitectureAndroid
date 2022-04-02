package com.hir0mu.cleanarchitecture.domain.repository

import com.hir0mu.cleanarchitecture.domain.model.ArticleModel

interface ArticleRepository {
    suspend fun getArticles(itemId: String, page: Int, perPage: Int): List<ArticleModel>

    suspend fun getArticleDetail(id: String): ArticleModel
}
