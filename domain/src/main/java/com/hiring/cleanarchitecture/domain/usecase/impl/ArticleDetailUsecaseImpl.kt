package com.hiring.cleanarchitecture.domain.usecase.impl

import com.hiring.cleanarchitecture.domain.mapper.ArticleMapper
import com.hiring.cleanarchitecture.domain.model.ArticleModel
import com.hiring.cleanarchitecture.domain.usecase.ArticleDetailUsecase
import com.hiring.data.repository.ArticleRepository
import com.hiring.data.repository.FavoriteRepository

class ArticleDetailUsecaseImpl(
    private val articleRepository: ArticleRepository,
    private val favoriteRepository: FavoriteRepository,
    private val articleMapper: ArticleMapper
) : ArticleDetailUsecase {
    override suspend fun fetchArticle(id: String): ArticleModel {
        val article = articleRepository.getArticleDetail(id)
        val isFavorite = favoriteRepository.getArticlesByIds(listOf(article.id)).isNotEmpty()
        return articleMapper.transform(article, isFavorite)
    }
}
