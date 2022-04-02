package com.hiring.cleanarchitecture.domain.repository

import com.hiring.cleanarchitecture.domain.model.ArticleModel

interface ArticleRepository {
    suspend fun getArticles(itemId: String, page: Int, perPage: Int): List<ArticleModel>

    suspend fun getArticleDetail(id: String): ArticleModel
}
