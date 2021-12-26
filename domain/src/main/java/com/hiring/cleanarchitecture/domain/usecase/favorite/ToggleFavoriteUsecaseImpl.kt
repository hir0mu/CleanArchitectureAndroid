package com.hiring.cleanarchitecture.domain.usecase.favorite

import com.hiring.cleanarchitecture.domain.model.ArticleModel
import com.hiring.data.entity.FavArticle
import com.hiring.data.entity.User
import com.hiring.data.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ToggleFavoriteUsecaseImpl(
    private val favoriteRepository: FavoriteRepository
): ToggleFavoriteUsecase {
    override fun toggleFavorite(article: ArticleModel): Flow<Unit> {
        return flow {
            if (article.isFavorite) {
                favoriteRepository.deleteByArticleId(article.id)
            } else {
                val favArticle = FavArticle(
                    id = article.id,
                    title = article.title,
                    url = article.url,
                    user = User(id = article.user.id, name = article.user.name, profileImageUrl = article.user.profileImageUrl)
                )
                favoriteRepository.insertAll(favArticle)
            }
            emit(Unit)
        }
    }
}
