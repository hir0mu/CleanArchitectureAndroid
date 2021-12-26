package com.hiring.cleanarchitecture.domain.usecase.article

import com.hiring.cleanarchitecture.domain.model.ArticleModel
import kotlinx.coroutines.flow.Flow

interface FetchArticleDetailUsecase {
    fun fetchArticle(id: String): Flow<ArticleModel>
}
