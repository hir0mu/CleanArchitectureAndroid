package com.hiring.cleanarchitecture.domain.usecase.article

import com.hiring.cleanarchitecture.domain.model.ArticleModel
import kotlinx.coroutines.flow.Flow

interface FetchArticleListUsecase {
    fun fetchArticles(itemId: String, page: Int): Flow<List<ArticleModel>>
}
