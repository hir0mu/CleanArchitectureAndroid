package com.hiring.data.repository.impl

import com.hiring.data.MemoryCache
import com.hiring.data.NetworkManager
import com.hiring.data.api.ArticleApi
import com.hiring.data.entity.Article
import com.hiring.data.repository.callWithCache
import com.hiring.data.repository.ArticleRepository
import com.hiring.data.repository.call

class ArticleRepositoryImpl(
    private val api: ArticleApi,
    private val networkManager: NetworkManager
): ArticleRepository {
    companion object {
        private val detailCaches = MemoryCache<Article>()
    }

    override suspend fun getArticles(itemId: String, page: Int, perPage: Int): List<Article> {
        return call(networkManager) { api.articles(itemId, page, perPage) }
    }

    override suspend fun getArticleDetail(id: String): Article {
        return callWithCache(networkManager, id, detailCaches) { api.articleDetail(id) }
    }
}
