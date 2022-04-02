package com.hir0mu.cleanarchitecture.domain.repository

import com.hir0mu.cleanarchitecture.domain.model.ArticleModel

interface FavoriteRepository {
    suspend fun getAll(): List<ArticleModel>

    suspend fun getArticlesByIds(articleIds: List<String>): List<ArticleModel>

    suspend fun insertAll(articles: List<ArticleModel>)

    suspend fun deleteByArticleId(articleId: String)
}
