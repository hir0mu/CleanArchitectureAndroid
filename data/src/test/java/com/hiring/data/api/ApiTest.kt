package com.hiring.data.api

import com.hiring.data.TestUtils
import com.hiring.data.entity.ArticleEntity
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
        assertNotEquals(emptyList<ArticleEntity>(), result)
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
