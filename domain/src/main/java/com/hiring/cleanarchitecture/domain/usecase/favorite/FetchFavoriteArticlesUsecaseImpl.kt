package com.hiring.cleanarchitecture.domain.usecase.favorite

import com.hiring.cleanarchitecture.domain.mapper.FavoriteArticleMapper
import com.hiring.cleanarchitecture.domain.model.ArticleModel
import com.hiring.data.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FetchFavoriteArticlesUsecaseImpl(
    private val favoriteRepository: FavoriteRepository,
    private val favoriteArticleMapper: FavoriteArticleMapper
): FetchFavoriteArticlesUsecase {
    override fun fetchFavoriteArticles(): Flow<List<ArticleModel>> {
        return flow {
            val models = favoriteRepository.getAll()
                .map { favoriteArticleMapper.transform(it) }
            emit(models)
        }
    }
}
