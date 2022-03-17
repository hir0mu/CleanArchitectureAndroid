package com.hiring.cleanarchitecture.domain.mapper

import com.hiring.cleanarchitecture.domain.model.ArticleModel
import com.hiring.cleanarchitecture.domain.model.UserModel
import com.hiring.data.entity.Article
import com.hiring.data.entity.User
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ArticleMapperTest {

    private lateinit var sut: ArticleMapper
    @Before
    fun setUp() {
        sut = ArticleMapper()
    }

    @Test
    fun testTransform_FavoriteIsFalse() {
        // Given
        val isFavorite = false
        val entity = article()
        val expected = articleModel(isFavorite)

        // When
        val result = sut.transform(entity, isFavorite)

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
        val result = sut.transform(entity, isFavorite)

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

        private fun article() = Article(
            renderedBody = "renderedBody",
            body = "body",
            coediting = false,
            commentsCount = 1,
            createdAt = "createdAt",
            group = null,
            id = ARTICLE_ID,
            likesCount = 1,
            private = false,
            reactionsCount = 1,
            tags = listOf(),
            title = ARTICLE_TITLE,
            updatedAt = "updatedAt",
            url = ARTICLE_URL,
            user = User(id = USER_ID, name = USER_NAME, profileImageUrl = USER_IMAGE_URL),
            pageViewsCount = 0
        )

        private fun articleModel(isFavorite: Boolean) = ArticleModel(
            id = ARTICLE_ID,
            title = ARTICLE_TITLE,
            url = ARTICLE_URL,
            user = UserModel(id = USER_ID, name = USER_NAME, profileImageUrl = USER_IMAGE_URL),
            isFavorite = isFavorite
        )
    }
}
