package com.hiring.cleanarchitecture.domain.mapper

import com.hiring.cleanarchitecture.domain.businessmodel.ArticleBusinessModel
import com.hiring.cleanarchitecture.domain.model.ArticleModel
import com.hiring.cleanarchitecture.domain.model.UserModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ArticleMapperTest {

    private lateinit var sut: ArticleBusinessModelMapper
    @Before
    fun setUp() {
        sut = ArticleBusinessModelMapper()
    }

    @Test
    fun testTransform_FavoriteIsFalse() {
        // Given
        val isFavorite = false
        val entity = article()
        val expected = articleModel(isFavorite)

        // When
        val result = sut.map(entity, isFavorite)

        // Then
        assertEquals(expected, result)
    }

    @Test
    fun testTransform_FavoriteIsTrue() {
        // Given
        val isFavorite = true
        val entity = article()
        val expected = articleModel(isFavorite)

        // When
        val result = sut.map(entity, isFavorite)

        // Then
        assertEquals(expected, result)
    }

    companion object {
        private const val ARTICLE_ID = "article_id"
        private const val ARTICLE_TITLE = "article_title"
        private const val ARTICLE_URL = "article_url"
        private const val USER_ID = "user_id"
        private const val USER_NAME = "user_name"
        private const val USER_IMAGE_URL = "user_image_url"

        private fun article() = ArticleModel(
            id = ARTICLE_ID,
            title = ARTICLE_TITLE,
            url = ARTICLE_URL,
            user = UserModel(id = USER_ID, name = USER_NAME, profileImageUrl = USER_IMAGE_URL),
        )

        private fun articleModel(isFavorite: Boolean) = ArticleBusinessModel(
            article = article(),
            isFavorite = isFavorite
        )
    }
}
