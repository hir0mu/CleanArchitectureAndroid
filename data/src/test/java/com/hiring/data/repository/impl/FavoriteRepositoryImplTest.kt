package com.hiring.data.repository.impl

import com.hiring.cleanarchitecture.domain.model.ArticleModel
import com.hiring.cleanarchitecture.domain.model.UserModel
import com.hiring.data.db.ArticleDao
import com.hiring.data.entity.FavArticleEntity
import com.hiring.data.entity.UserEntity
import com.hiring.data.mapper.ArticleModelMapper
import com.hiring.data.repository.FavoriteRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.given

@ExperimentalCoroutinesApi
class FavoriteRepositoryImplTest {
    @Mock
    private lateinit var dao: ArticleDao

    private lateinit var sut: FavoriteRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        sut = FavoriteRepositoryImpl(dao, ArticleModelMapper())
    }

    @Test
    fun testGetAll() = runTest {
        // GIVEN
        given(dao.getAll()).willReturn(FAV_ARTICLES)

        // WHEN
        val result = sut.getAll()

        // THEN
        Assert.assertEquals(ARTICLE_MODELS, result)
        then(dao).should().getAll()
    }

    @Test
    fun testInsertAll() = runTest {
        // GIVEN

        // WHEN
        sut.insertAll(ARTICLE_MODELS)

        // THEN
        then(dao).should().insertAll(any())
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
            FavArticleEntity(
                id = "",
                title = "title",
                url = "url",
                user = UserEntity(
                    id = "id",
                    name = "name",
                    profileImageUrl = "url"
                ),
            )
        )

        private val ARTICLE_MODELS = listOf(
            ArticleModel(
                id = "",
                title = "title",
                url = "url",
                user = UserModel(
                    id = "id",
                    name = "name",
                    profileImageUrl = "url"
                ),
            )
        )
    }
}
