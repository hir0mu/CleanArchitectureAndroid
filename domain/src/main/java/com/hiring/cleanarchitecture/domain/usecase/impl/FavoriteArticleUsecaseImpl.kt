package com.hiring.cleanarchitecture.domain.usecase.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.hiring.cleanarchitecture.domain.mapper.FavoriteArticleMapper
import com.hiring.cleanarchitecture.domain.model.ArticleModel
import com.hiring.cleanarchitecture.domain.usecase.FavoriteArticleUsecase
import com.hiring.data.repository.FavoriteRepository

class FavoriteArticleUsecaseImpl(
    private val favoriteRepository: FavoriteRepository,
    private val favoriteArticleMapper: FavoriteArticleMapper
): FavoriteArticleUsecase {
    override fun favoriteArticlesLiveData(): LiveData<List<ArticleModel>> {
        return favoriteRepository.listArticlesLiveData()
            .map { articles ->
                articles.map { favoriteArticleMapper.transform(it) }
            }
    }
}
