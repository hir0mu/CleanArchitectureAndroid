package com.hiring.cleanarchitecture.domain.usecase.favorite

import com.hiring.cleanarchitecture.domain.businessmodel.ArticleListBusinessModel
import com.hiring.cleanarchitecture.domain.mapper.ArticleBusinessModelMapper
import com.hiring.cleanarchitecture.domain.mapper.ArticleListBusinessModelMapper
import com.hiring.cleanarchitecture.domain.repository.FavoriteRepository
import com.hiring.cleanarchitecture.domain.usecase.Usecase
import com.hiring.cleanarchitecture.domain.usecase.EmptyUsecaseInput
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

typealias FetchFavoriteArticleListUsecase = Usecase<EmptyUsecaseInput, ArticleListBusinessModel>

class FetchFavoriteArticleListUsecaseImpl(
    private val favoriteRepository: FavoriteRepository,
    private val articleMapper: ArticleBusinessModelMapper,
    private val articleListMapper: ArticleListBusinessModelMapper
) : FetchFavoriteArticleListUsecase {
    override fun execute(input: EmptyUsecaseInput): Flow<ArticleListBusinessModel> {
        return flow {
            val models = favoriteRepository.getAll()
                .map { articleMapper.map(it, true) }
                .let { articleListMapper.map(it) }
            emit(models)
        }
    }
}
