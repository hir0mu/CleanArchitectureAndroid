package com.hiring.cleanarchitecture.domain.usecase

import com.hiring.cleanarchitecture.domain.model.ArticleModel
import kotlinx.coroutines.flow.Flow

interface ArticleListUsecase {
    fun fetchArticles(itemId: String): Flow<List<ArticleModel>>

    fun toggleFavorite(article: ArticleModel): Flow<Unit>
}
