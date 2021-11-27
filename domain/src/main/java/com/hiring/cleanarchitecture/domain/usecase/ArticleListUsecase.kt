package com.hiring.cleanarchitecture.domain.usecase

import com.hiring.cleanarchitecture.domain.model.ArticleModel

interface ArticleListUsecase {
    suspend fun fetchArticles(itemId: String): List<ArticleModel>

    suspend fun toggleFavorite(article: ArticleModel)
}
