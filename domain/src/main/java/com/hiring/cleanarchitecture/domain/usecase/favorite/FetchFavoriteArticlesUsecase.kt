package com.hiring.cleanarchitecture.domain.usecase.favorite

import com.hiring.cleanarchitecture.domain.model.ArticleModel
import kotlinx.coroutines.flow.Flow

interface FetchFavoriteArticlesUsecase {
    fun fetchFavoriteArticles(): Flow<List<ArticleModel>>
}
