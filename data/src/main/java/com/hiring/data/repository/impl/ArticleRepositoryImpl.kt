package com.hiring.data.repository.impl

import com.hiring.data.MemoryCache
import com.hiring.data.api.ArticleApi
import com.hiring.data.entity.Article
import com.hiring.data.repository.call
import com.hiring.data.repository.ArticleRepository

class ArticleRepositoryImpl(
    private val api: ArticleApi
): ArticleRepository {
    companion object {
        private val detailCaches = MemoryCache<Article>()
    }

    override suspend fun getArticles(itemId: String, page: Int, perPage: Int): List<Article> {
        return api.articles(itemId, page, perPage)
    }

    override suspend fun getArticleDetail(id: String): Article {
        return call(id, detailCaches) { api.articleDetail(id) }
    }
}
