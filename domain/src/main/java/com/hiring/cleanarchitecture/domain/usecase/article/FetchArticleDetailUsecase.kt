package com.hiring.cleanarchitecture.domain.usecase.article

import com.hiring.cleanarchitecture.domain.businessmodel.ArticleBusinessModel
import com.hiring.cleanarchitecture.domain.mapper.ArticleBusinessModelMapper
import com.hiring.cleanarchitecture.domain.repository.ArticleRepository
import com.hiring.cleanarchitecture.domain.repository.FavoriteRepository
import com.hiring.cleanarchitecture.domain.usecase.Usecase
import com.hiring.cleanarchitecture.domain.usecase.UsecaseInput
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

typealias FetchArticleDetailUsecase = Usecase<FetchArticleDetailInput, ArticleBusinessModel>

data class FetchArticleDetailInput(
    val id: String
) : UsecaseInput

class FetchArticleDetailUsecaseImpl(
    private val articleRepository: ArticleRepository,
    private val favoriteRepository: FavoriteRepository,
    private val articleMapper: ArticleBusinessModelMapper
) : FetchArticleDetailUsecase {
    override fun execute(input: FetchArticleDetailInput): Flow<ArticleBusinessModel> {
        val (id) = input
        return flow {
            val article = articleRepository.getArticleDetail(id)
            val isFavorite = favoriteRepository.getArticlesByIds(listOf(article.id)).isNotEmpty()
            emit(articleMapper.map(article, isFavorite))
        }
    }
}
