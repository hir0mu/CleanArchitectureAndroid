package com.hiring.data.repository.impl

import com.hiring.data.db.ArticleDao
import com.hiring.data.entity.FavArticle
import com.hiring.data.repository.FavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoriteRepositoryImpl(
    private val articleDao: ArticleDao
) : FavoriteRepository {
    override suspend fun getAll(): List<FavArticle> {
        return withContext(Dispatchers.IO) {
            articleDao.getAll()
        }
    }

    override suspend fun getArticlesByIds(articleIds: List<String>): List<FavArticle> {
        return withContext(Dispatchers.IO) {
            articleDao.getArticlesByIds(articleIds)
        }
    }

    override suspend fun insertAll(vararg articles: FavArticle) {
        return withContext(Dispatchers.IO) {
            articleDao.insertAll(articles.toList())
        }
    }

    override suspend fun deleteByArticleId(articleId: String) {
        return withContext(Dispatchers.IO) {
            articleDao.deleteByArticleId(articleId)
        }
    }
}
