package com.hiring.data.repository

import com.hiring.cleanarchitecture.domain.model.ArticleModel
import com.hiring.cleanarchitecture.domain.repository.FavoriteRepository
import com.hiring.data.db.ArticleDao
import com.hiring.data.entity.FavArticle
import com.hiring.data.entity.User
import com.hiring.data.mapper.ArticleModelMapper

class FavoriteRepositoryImpl(
    private val articleDao: ArticleDao,
    private val articleMapper: ArticleModelMapper
) : FavoriteRepository {
    override suspend fun getAll(): List<ArticleModel> {
        return articleDao.getAll()
            .let { entities ->
                entities.map { articleMapper.map(it) }
            }
    }

    override suspend fun getArticlesByIds(articleIds: List<String>): List<ArticleModel> {
        return articleDao.getArticlesByIds(articleIds)
            .let { entities ->
                entities.map { articleMapper.map(it) }
            }
    }

    override suspend fun insertAll(articles: List<ArticleModel>) {
        val favs = articles.map {
            FavArticle(
                id = it.id,
                title = it.title,
                url = it.url,
                user = User(
                    id = it.user.id,
                    name = it.user.name,
                    profileImageUrl = it.user.profileImageUrl
                )
            )
        }
        return articleDao.insertAll(favs)
    }

    override suspend fun deleteByArticleId(articleId: String) {
        return articleDao.deleteByArticleId(articleId)
    }
}
