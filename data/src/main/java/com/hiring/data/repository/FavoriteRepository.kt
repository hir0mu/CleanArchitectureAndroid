package com.hiring.data.repository

import com.hiring.data.entity.FavArticle

interface FavoriteRepository {
    suspend fun getAll(): List<FavArticle>

    suspend fun getArticlesByIds(articleIds: List<String>): List<FavArticle>

    suspend fun insertAll(articles: List<FavArticle>)

    suspend fun deleteByArticleId(articleId: String)
}
