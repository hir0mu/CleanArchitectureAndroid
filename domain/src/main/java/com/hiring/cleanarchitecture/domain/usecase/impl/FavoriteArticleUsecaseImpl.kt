package com.hiring.cleanarchitecture.domain.usecase.impl

import com.hiring.cleanarchitecture.domain.mapper.FavoriteArticleMapper
import com.hiring.cleanarchitecture.domain.model.ArticleModel
import com.hiring.cleanarchitecture.domain.usecase.FavoriteArticleUsecase
import com.hiring.data.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class FavoriteArticleUsecaseImpl(
    private val favoriteRepository: FavoriteRepository,
    private val favoriteArticleMapper: FavoriteArticleMapper
): FavoriteArticleUsecase {
    override fun fetchFavoriteArticles(): Flow<List<ArticleModel>> {
        return flow {
            val models = favoriteRepository.getAll()
                .map { favoriteArticleMapper.transform(it) }
            emit(models)
        }
    }
}
