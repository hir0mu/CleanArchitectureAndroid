package com.hiring.cleanarchitecture.domain.usecase.article

import com.hiring.cleanarchitecture.domain.businessmodel.ArticleBusinessModel
import com.hiring.cleanarchitecture.domain.businessmodel.ArticleListBusinessModel
import com.hiring.cleanarchitecture.domain.mapper.ArticleBusinessModelMapper
import com.hiring.cleanarchitecture.domain.mapper.ArticleListBusinessModelMapper
import com.hiring.cleanarchitecture.domain.model.ArticleModel
import com.hiring.cleanarchitecture.domain.model.UserModel
import com.hiring.cleanarchitecture.domain.repository.ArticleRepository
import com.hiring.cleanarchitecture.domain.repository.FavoriteRepository
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
            ArticleBusinessModelMapper(),
            ArticleListBusinessModelMapper()
        )
    }

    @Test
    fun testFetchArticles_hasFavorite() = runTest {
        // Given
        given(articleRepository.getArticles(ITEM_ID, PAGE, PER_PAGE)).willReturn(listOf(article()))
        given(favoriteRepository.getArticlesByIds(listOf(ARTICLE_ID))).willReturn(listOf(article()))
        val model = articleListModel(true)

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
        val model = articleListModel(false)

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
