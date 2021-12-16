package com.hiring.cleanarchitecture.domain.usecase.impl

import com.hiring.cleanarchitecture.domain.mapper.ArticleMapper
import com.hiring.cleanarchitecture.domain.model.ArticleModel
import com.hiring.cleanarchitecture.domain.usecase.ArticleListUsecase
import com.hiring.data.entity.FavArticle
import com.hiring.data.entity.User
import com.hiring.data.repository.ArticleRepository
import com.hiring.data.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ArticleListUsecaseImpl(
    private val articleRepository: ArticleRepository,
    private val favoriteRepository: FavoriteRepository,
    private val articleMapper: ArticleMapper
) : ArticleListUsecase {

    companion object {
        private const val PER_PAGE = 20
    }

    override fun fetchArticles(itemId: String, page: Int): Flow<List<ArticleModel>> {
        return flow {
            val articles = articleRepository.getArticles(itemId, page, PER_PAGE)
            val favs = favoriteRepository.getArticlesByIds(articles.map { it.id })

            val models = articles.map { entity ->
                articleMapper.transform(entity, favs.any { it.id == entity.id })
            }

            emit(models)
        }
    }

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
