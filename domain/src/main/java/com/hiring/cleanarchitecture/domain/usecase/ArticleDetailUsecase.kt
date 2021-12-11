package com.hiring.cleanarchitecture.domain.usecase

import com.hiring.cleanarchitecture.domain.model.ArticleModel
import kotlinx.coroutines.flow.Flow

interface ArticleDetailUsecase {
    fun fetchArticle(id: String): Flow<ArticleModel>
}
