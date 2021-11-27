package com.hiring.data.repository.impl

import com.hiring.data.db.ArticleDao
import com.hiring.data.entity.FavArticle
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.then
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class FavoriteRepositoryImplTest {
    private lateinit var sut: FavoriteRepositoryImpl

    @Test
    fun testGetAll() {
        // GIVEN
        val dao = mock<ArticleDao> {
            onBlocking { getAll() } doReturn FAV_ARTICLES
        }
        sut = FavoriteRepositoryImpl(dao)

        // WHEN
        val result = runBlocking { sut.getAll() }

        // THEN
        Assert.assertEquals(FAV_ARTICLES, result)
        runBlocking { then(dao).should().getAll() }
    }

    @Test
    fun testInsertAll() {
        // GIVEN
        val dao = mock<ArticleDao>()
        sut = FavoriteRepositoryImpl(dao)

        // WHEN
        runBlocking { sut.insertAll(FAV_ARTICLE) }

        // THEN
        runBlocking { then(dao).should().insertAll(listOf(FAV_ARTICLE)) }
    }

    @Test
    fun testDeleteByArticleId() {
        // GIVEN
        val id = "id"
        val dao = mock<ArticleDao>()
        sut = FavoriteRepositoryImpl(dao)

        // WHEN
        runBlocking { sut.deleteByArticleId(id) }

        // THEN
        runBlocking { then(dao).should().deleteByArticleId(id) }
    }

    companion object {
        private val FAV_ARTICLE = FavArticle(
            id = "",
            title = "title",
            url = "url",
            user = null,
        )

        private val FAV_ARTICLES = listOf(FAV_ARTICLE)
    }
}
