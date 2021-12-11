package com.hiring.cleanarchitecture.domain.usecase.impl

import com.hiring.cleanarchitecture.domain.mapper.ArticleMapper
import com.hiring.cleanarchitecture.domain.model.ArticleModel
import com.hiring.cleanarchitecture.domain.usecase.ArticleDetailUsecase
import com.hiring.data.repository.ArticleRepository
import com.hiring.data.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow

class ArticleDetailUsecaseImpl(
    private val articleRepository: ArticleRepository,
    private val favoriteRepository: FavoriteRepository,
    private val articleMapper: ArticleMapper
) : ArticleDetailUsecase {
    override fun fetchArticle(id: String): Flow<ArticleModel> {
        return flow {
            val article = articleRepository.getArticleDetail(id)
            val isFavorite = favoriteRepository.getArticlesByIds(listOf(article.id)).isNotEmpty()
            emit(articleMapper.transform(article, isFavorite))
        }
    }
}
