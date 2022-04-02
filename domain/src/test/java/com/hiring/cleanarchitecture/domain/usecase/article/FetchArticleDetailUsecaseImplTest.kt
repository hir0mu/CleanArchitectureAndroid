package com.hiring.cleanarchitecture.domain.usecase.article

import com.hiring.cleanarchitecture.domain.mapper.ArticleMapper
import com.hiring.cleanarchitecture.domain.model.ArticleModel
import com.hiring.cleanarchitecture.domain.model.UserModel
import com.hiring.data.entity.Article
import com.hiring.data.entity.FavArticle
import com.hiring.data.entity.User
import com.hiring.data.repository.ArticleRepository
import com.hiring.data.repository.FavoriteRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.given
import org.mockito.kotlin.then

@ExperimentalCoroutinesApi
class FetchArticleDetailUsecaseImplTest {

    private lateinit var sut: FetchArticleDetailUsecaseImpl

    @Mock
    private lateinit var articleRepository: ArticleRepository

    @Mock
    private lateinit var favoriteRepository: FavoriteRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        sut = FetchArticleDetailUsecaseImpl(
            articleRepository,
            favoriteRepository,
            ArticleMapper()
        )
    }

    @Test
    fun testFetchArticle_hasFavorite() = runTest {
        // Given
        given(articleRepository.getArticleDetail(ARTICLE_ID)).willReturn(article())
        given(favoriteRepository.getArticlesByIds(listOf(ARTICLE_ID))).willReturn(listOf(favArticle()))
        val model = articleModel(true)

        // When
        val result = sut.execute(ARGS).first()

        // Then
        assertEquals(model, result)
        then(articleRepository).should().getArticleDetail(ARTICLE_ID)
        then(favoriteRepository).should().getArticlesByIds(listOf(ARTICLE_ID))
    }

    @Test
    fun testFetchArticle_noFavorite() = runTest {
        // Given
        given(articleRepository.getArticleDetail(ARTICLE_ID)).willReturn(article())
        given(favoriteRepository.getArticlesByIds(listOf(ARTICLE_ID))).willReturn(listOf())
        val model = articleModel(false)

        // When
        val result = sut.execute(ARGS).first()

        // Then
        assertEquals(model, result)
        then(articleRepository).should().getArticleDetail(ARTICLE_ID)
        then(favoriteRepository).should().getArticlesByIds(listOf(ARTICLE_ID))
    }

    companion object {
        private const val ARTICLE_ID = "article_id"
        private const val ARTICLE_TITLE = "article_title"
        private const val ARTICLE_URL = "article_url"
        private const val USER_ID = "user_id"
        private const val USER_NAME = "user_name"
        private const val USER_IMAGE_URL = "user_image_url"

        private val ARGS = FetchArticleDetailArgs(ARTICLE_ID)

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

        private fun favArticle() = FavArticle(
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
