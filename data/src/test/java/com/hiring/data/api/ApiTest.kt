package com.hir0mu.data.api

import com.hir0mu.cleanarchitecture.data.api.ArticleApi
import com.hir0mu.data.TestUtils
import com.hir0mu.cleanarchitecture.data.entity.ArticleEntity
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
