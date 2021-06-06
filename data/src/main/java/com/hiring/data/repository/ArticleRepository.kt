package com.hiring.data.repository

import com.hiring.data.entity.Article

interface ArticleRepository {
    suspend fun getArticles(itemId: String, page: Int, perPage: Int): List<Article>

    suspend fun getArticleDetail(id: String): Article
}
