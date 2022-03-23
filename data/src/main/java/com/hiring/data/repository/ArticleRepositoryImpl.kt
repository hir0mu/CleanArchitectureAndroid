package com.hiring.data.repository

import com.hiring.cleanarchitecture.domain.model.ArticleModel
import com.hiring.cleanarchitecture.domain.repository.ArticleRepository
import com.hiring.data.MemoryCache
import com.hiring.data.NetworkManager
import com.hiring.data.api.ArticleApi
import com.hiring.data.entity.Article
import com.hiring.data.mapper.ArticleModelMapper

class ArticleRepositoryImpl(
    private val api: ArticleApi,
    private val networkManager: NetworkManager,
    private val articleMapper: ArticleModelMapper
): ArticleRepository {
    companion object {
        private val detailCaches = MemoryCache<Article>()
    }

    override suspend fun getArticles(itemId: String, page: Int, perPage: Int): List<ArticleModel> {
        return call(networkManager) { api.articles(itemId, page, perPage) }
            .let { entities ->
                entities.map { articleMapper.map(it) }
            }
    }

    override suspend fun getArticleDetail(id: String): ArticleModel {
        return callWithCache(networkManager, id, detailCaches) { api.articleDetail(id) }
            .let { articleMapper.map(it) }
    }
}
