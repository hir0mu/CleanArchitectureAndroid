package com.hiring.cleanarchitecture.domain.usecase.favorite

import com.hiring.cleanarchitecture.domain.mapper.FavoriteArticleMapper
import com.hiring.cleanarchitecture.domain.model.ArticleModel
import com.hiring.cleanarchitecture.domain.usecase.Usecase
import com.hiring.cleanarchitecture.domain.usecase.UsecaseArgsUnit
import com.hiring.data.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

typealias FetchFavoriteArticleListUsecase = Usecase<UsecaseArgsUnit, List<ArticleModel>>

class FetchFavoriteArticleListUsecaseImpl(
    private val favoriteRepository: FavoriteRepository,
    private val favoriteArticleMapper: FavoriteArticleMapper
) : FetchFavoriteArticleListUsecase {
    override fun execute(args: UsecaseArgsUnit): Flow<List<ArticleModel>> {
        return flow {
            val models = favoriteRepository.getAll()
                .map { favoriteArticleMapper.transform(it) }
            emit(models)
        }
    }
}
