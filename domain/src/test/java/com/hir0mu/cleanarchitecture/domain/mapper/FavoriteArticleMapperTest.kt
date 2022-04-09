package com.hir0mu.cleanarchitecture.domain.mapper

import com.hir0mu.cleanarchitecture.domain.businessmodel.ArticleBusinessModel
import com.hir0mu.cleanarchitecture.domain.businessmodel.ArticleListBusinessModel
import com.hir0mu.cleanarchitecture.domain.mapper.ArticleListBusinessModelMapper
import com.hir0mu.cleanarchitecture.domain.model.ArticleModel
import com.hir0mu.cleanarchitecture.domain.model.UserModel
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class FavoriteArticleMapperTest {

    private lateinit var sut: ArticleListBusinessModelMapper

    @Before
    fun setUp() {
        sut = ArticleListBusinessModelMapper()
    }

    @Test
    fun testTransform() {
        // Given
        val model = articleModel(true)
        val expected = articleListModel(true)

        // When
        val result = sut.map(listOf(model))

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

        private fun articleListModel(isFavorite: Boolean) = ArticleListBusinessModel(
            articles = listOf(articleModel(isFavorite)),
        )
    }
}
