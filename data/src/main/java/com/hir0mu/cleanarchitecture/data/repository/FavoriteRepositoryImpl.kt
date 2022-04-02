package com.hir0mu.cleanarchitecture.data.repository

import com.hir0mu.cleanarchitecture.domain.model.ArticleModel
import com.hir0mu.cleanarchitecture.domain.repository.FavoriteRepository
import com.hir0mu.cleanarchitecture.data.db.ArticleDao
import com.hir0mu.cleanarchitecture.data.entity.FavArticleEntity
import com.hir0mu.cleanarchitecture.data.entity.UserEntity
import com.hir0mu.cleanarchitecture.data.mapper.ArticleModelMapper

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
            FavArticleEntity(
                id = it.id,
                title = it.title,
                url = it.url,
                user = UserEntity(
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
