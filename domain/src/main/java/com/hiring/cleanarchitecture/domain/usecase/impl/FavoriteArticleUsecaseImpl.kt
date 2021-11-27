package com.hiring.cleanarchitecture.domain.usecase.impl

import com.hiring.cleanarchitecture.domain.mapper.FavoriteArticleMapper
import com.hiring.cleanarchitecture.domain.model.ArticleModel
import com.hiring.cleanarchitecture.domain.usecase.FavoriteArticleUsecase
import com.hiring.data.repository.FavoriteRepository

class FavoriteArticleUsecaseImpl(
    private val favoriteRepository: FavoriteRepository,
    private val favoriteArticleMapper: FavoriteArticleMapper
): FavoriteArticleUsecase {
    override suspend fun fetchFavoriteArticles(): List<ArticleModel> {
        return favoriteRepository.getAll()
            .map { favoriteArticleMapper.transform(it) }
    }
}
