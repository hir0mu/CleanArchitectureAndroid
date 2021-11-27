package com.hiring.cleanarchitecture.domain.usecase

import com.hiring.cleanarchitecture.domain.model.ArticleModel

interface FavoriteArticleUsecase {
    suspend fun fetchFavoriteArticles(): List<ArticleModel>
}
