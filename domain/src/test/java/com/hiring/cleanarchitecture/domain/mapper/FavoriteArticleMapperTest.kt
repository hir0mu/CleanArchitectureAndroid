package com.hiring.cleanarchitecture.domain.mapper

import com.hiring.cleanarchitecture.domain.model.ArticleModel
import com.hiring.cleanarchitecture.domain.model.UserModel
import com.hiring.data.entity.FavArticle
import com.hiring.data.entity.User
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class FavoriteArticleMapperTest {

    private lateinit var sut: FavoriteArticleMapper

    @Before
    fun setUp() {
        sut = FavoriteArticleMapper()
    }

    @Test
    fun testTransform() {
        // Given
        val entity = article()
        val model = articleModel(true)

        // When
        val result = sut.transform(entity)

        // Then
        assertEquals(model, result)
    }

    companion object {
        private const val ARTICLE_ID = "article_id"
        private const val ARTICLE_TITLE = "article_title"
        private const val ARTICLE_URL = "article_url"
        private const val USER_ID = "user_id"
        private const val USER_NAME = "user_name"
        private const val USER_IMAGE_URL = "user_image_url"

        private fun article() = FavArticle(
            createdAt = 0,
            id = ARTICLE_ID,
            title = ARTICLE_TITLE,
            url = ARTICLE_URL,
            user = User(id = USER_ID, name = USER_NAME, profileImageUrl = USER_IMAGE_URL),
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
