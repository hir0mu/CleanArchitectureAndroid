package com.hiring.cleanarchitecture.domain.usecase.article

import com.hiring.cleanarchitecture.domain.mapper.ArticleMapper
import com.hiring.cleanarchitecture.domain.model.ArticleModel
import com.hiring.data.repository.ArticleRepository
import com.hiring.data.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FetchArticleDetailUsecaseImpl(
    private val articleRepository: ArticleRepository,
    private val favoriteRepository: FavoriteRepository,
    private val articleMapper: ArticleMapper
) : FetchArticleDetailUsecase {
    override fun fetchArticle(id: String): Flow<ArticleModel> {
        return flow {
            val article = articleRepository.getArticleDetail(id)
            val isFavorite = favoriteRepository.getArticlesByIds(listOf(article.id)).isNotEmpty()
            emit(articleMapper.transform(article, isFavorite))
        }
    }
}
