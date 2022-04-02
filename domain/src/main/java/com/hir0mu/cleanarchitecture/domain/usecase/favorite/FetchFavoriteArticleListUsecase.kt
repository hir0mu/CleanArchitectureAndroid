package com.hir0mu.cleanarchitecture.domain.usecase.favorite

import com.hir0mu.cleanarchitecture.domain.businessmodel.ArticleListBusinessModel
import com.hir0mu.cleanarchitecture.domain.mapper.ArticleBusinessModelMapper
import com.hir0mu.cleanarchitecture.domain.mapper.ArticleListBusinessModelMapper
import com.hir0mu.cleanarchitecture.domain.repository.FavoriteRepository
import com.hir0mu.cleanarchitecture.domain.usecase.Usecase
import com.hir0mu.cleanarchitecture.domain.usecase.EmptyUsecaseInput
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
