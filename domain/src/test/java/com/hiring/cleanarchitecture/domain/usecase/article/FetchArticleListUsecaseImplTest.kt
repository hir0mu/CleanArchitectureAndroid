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
class FetchArticleListUsecaseImplTest {

    private lateinit var sut: FetchArticleListUsecaseImpl

    @Mock
    private lateinit var articleRepository: ArticleRepository

    @Mock
    private lateinit var favoriteRepository: FavoriteRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        sut = FetchArticleListUsecaseImpl(
            articleRepository,
            favoriteRepository,
            ArticleMapper()
        )
    }

    @Test
    fun testFetchArticles_hasFavorite() = runTest {
        // Given
        given(articleRepository.getArticles(ITEM_ID, PAGE, PER_PAGE)).willReturn(listOf(article()))
        given(favoriteRepository.getArticlesByIds(listOf(ARTICLE_ID))).willReturn(listOf(favArticle()))
        val model = listOf(articleModel(true))

        // When
        val result = sut.execute(ARGS).first()

        // Then
        assertEquals(model, result)
        then(articleRepository).should().getArticles(ITEM_ID, PAGE, PER_PAGE)
        then(favoriteRepository).should().getArticlesByIds(listOf(ARTICLE_ID))
    }

    @Test
    fun testFetchArticles_noFavorite() = runTest {
        // Given
        given(articleRepository.getArticles(ITEM_ID, PAGE, PER_PAGE)).willReturn(listOf(article()))
        given(favoriteRepository.getArticlesByIds(listOf(ARTICLE_ID))).willReturn(listOf())
        val model = listOf(articleModel(false))

        // When
        val result = sut.execute(ARGS).first()

        // Then
        assertEquals(model, result)
        then(articleRepository).should().getArticles(ITEM_ID, PAGE, PER_PAGE)
        then(favoriteRepository).should().getArticlesByIds(listOf(ARTICLE_ID))
    }

    companion object {
        private const val ITEM_ID = "item_id"
        private const val PAGE = 0
        private const val PER_PAGE = 20

        private const val ARTICLE_ID = "article_id"
        private const val ARTICLE_TITLE = "article_title"
        private const val ARTICLE_URL = "article_url"
        private const val USER_ID = "user_id"
        private const val USER_NAME = "user_name"
        private const val USER_IMAGE_URL = "user_image_url"

        private val ARGS = FetchArticleListArgs(ITEM_ID, PAGE)

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
