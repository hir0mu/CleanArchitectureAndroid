package com.hiring.data.repository.impl

import com.hiring.data.db.ArticleDao
import com.hiring.data.entity.FavArticle
import com.hiring.data.repository.FavoriteRepository

class FavoriteRepositoryImpl(
    private val articleDao: ArticleDao
) : FavoriteRepository {
    override suspend fun getAll(): List<FavArticle> {
        return articleDao.getAll()
    }

    override suspend fun getArticlesByIds(articleIds: List<String>): List<FavArticle> {
        return articleDao.getArticlesByIds(articleIds)
    }

    override suspend fun insertAll(articles: List<FavArticle>) {
        return articleDao.insertAll(articles)
    }

    override suspend fun deleteByArticleId(articleId: String) {
        return articleDao.deleteByArticleId(articleId)
    }
}
