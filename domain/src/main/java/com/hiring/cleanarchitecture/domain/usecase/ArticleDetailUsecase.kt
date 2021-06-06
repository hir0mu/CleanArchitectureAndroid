package com.hiring.cleanarchitecture.domain.usecase

import com.hiring.cleanarchitecture.domain.model.ArticleModel

interface ArticleDetailUsecase {
    suspend fun fetchArticle(id: String): ArticleModel
}
