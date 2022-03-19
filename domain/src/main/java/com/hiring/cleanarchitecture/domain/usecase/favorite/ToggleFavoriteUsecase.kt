package com.hiring.cleanarchitecture.domain.usecase.favorite

import com.hiring.cleanarchitecture.domain.model.ArticleModel
import com.hiring.cleanarchitecture.domain.usecase.Usecase
import com.hiring.cleanarchitecture.domain.usecase.UsecaseArgs
import com.hiring.data.entity.FavArticle
import com.hiring.data.entity.User
import com.hiring.data.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

typealias ToggleFavoriteUsecase = Usecase<ToggleFavoriteUsecaseArgs, Unit>

data class ToggleFavoriteUsecaseArgs(
    val article: ArticleModel
) : UsecaseArgs

class ToggleFavoriteUsecaseImpl(
    private val favoriteRepository: FavoriteRepository
) : ToggleFavoriteUsecase {
    override fun execute(args: ToggleFavoriteUsecaseArgs): Flow<Unit> {
        val (article) = args
        return flow {
            if (article.isFavorite) {
                favoriteRepository.deleteByArticleId(article.id)
            } else {
                val favArticle = FavArticle(
                    id = article.id,
                    title = article.title,
                    url = article.url,
                    user = User(
                        id = article.user.id,
                        name = article.user.name,
                        profileImageUrl = article.user.profileImageUrl
                    )
                )
                favoriteRepository.insertAll(listOf(favArticle))
            }
            emit(Unit)
        }
    }
}
