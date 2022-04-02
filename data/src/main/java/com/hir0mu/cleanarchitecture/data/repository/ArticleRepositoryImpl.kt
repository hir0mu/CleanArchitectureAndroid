package com.hir0mu.cleanarchitecture.data.repository

import com.hir0mu.cleanarchitecture.domain.model.ArticleModel
import com.hir0mu.cleanarchitecture.domain.repository.ArticleRepository
import com.hir0mu.cleanarchitecture.data.MemoryCache
import com.hir0mu.cleanarchitecture.data.NetworkManager
import com.hir0mu.cleanarchitecture.data.api.ArticleApi
import com.hir0mu.cleanarchitecture.data.entity.ArticleEntity
import com.hir0mu.cleanarchitecture.data.mapper.ArticleModelMapper

class ArticleRepositoryImpl(
    private val api: ArticleApi,
    private val networkManager: NetworkManager,
    private val articleMapper: ArticleModelMapper
) : ArticleRepository {
    companion object {
        private val detailCaches = MemoryCache<ArticleEntity>()
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
