package com.hiring.data

import com.hiring.data.api.ArticleApi
import com.hiring.data.entity.Article
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

class ApiTest {
    private lateinit var sut: ArticleApi

    @Before
    fun setup() {
        sut = TestUtils.retrofit.create(ArticleApi::class.java)
    }

    @Test
    fun testFetchArticles() {
        val result = runBlocking { sut.articles("android", 1, 20) }
        assertNotEquals(emptyList<Article>(), result)
    }

    @Test
    fun testFetchArticleDetail() {
        val id = runBlocking {
            sut.articles("android", 1, 20).firstOrNull()?.id
        }
        val result = runBlocking { sut.articleDetail(checkNotNull(id)) }
        assertNotEquals(null, result)
    }
}
