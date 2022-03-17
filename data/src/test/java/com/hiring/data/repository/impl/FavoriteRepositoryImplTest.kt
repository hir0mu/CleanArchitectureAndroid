package com.hiring.data.repository.impl

import com.hiring.data.db.ArticleDao
import com.hiring.data.entity.FavArticle
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.given

@OptIn(ExperimentalCoroutinesApi::class)
class FavoriteRepositoryImplTest {
    @Mock
    private lateinit var dao: ArticleDao

    private lateinit var sut: FavoriteRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        sut = FavoriteRepositoryImpl(dao)
    }

    @Test
    fun testGetAll() = runTest {
        // GIVEN
        given(dao.getAll()).willReturn(FAV_ARTICLES)

        // WHEN
        val result = sut.getAll()

        // THEN
        Assert.assertEquals(FAV_ARTICLES, result)
        then(dao).should().getAll()
    }

    @Test
    fun testInsertAll() = runTest {
        // GIVEN

        // WHEN
        sut.insertAll(FAV_ARTICLES)

        // THEN
        then(dao).should().insertAll(FAV_ARTICLES)
    }

    @Test
    fun testDeleteByArticleId() = runTest {
        // GIVEN
        val id = "id"

        // WHEN
        sut.deleteByArticleId(id)

        // THEN
        then(dao).should().deleteByArticleId(id)
    }

    companion object {
        private val FAV_ARTICLES = listOf(
            FavArticle(
                id = "",
                title = "title",
                url = "url",
                user = null,
            )
        )
    }
}
