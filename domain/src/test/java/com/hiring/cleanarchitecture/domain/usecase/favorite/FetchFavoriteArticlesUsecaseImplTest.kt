package com.hiring.cleanarchitecture.domain.usecase.favorite

import com.hiring.cleanarchitecture.domain.businessmodel.ArticleBusinessModel
import com.hiring.cleanarchitecture.domain.businessmodel.ArticleListBusinessModel
import com.hiring.cleanarchitecture.domain.mapper.ArticleBusinessModelMapper
import com.hiring.cleanarchitecture.domain.mapper.ArticleListBusinessModelMapper
import com.hiring.cleanarchitecture.domain.model.ArticleModel
import com.hiring.cleanarchitecture.domain.model.UserModel
import com.hiring.cleanarchitecture.domain.repository.FavoriteRepository
import com.hiring.cleanarchitecture.domain.usecase.EmptyUsecaseInput
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
class FetchFavoriteArticlesUsecaseImplTest {

    private lateinit var sut: FetchFavoriteArticleListUsecaseImpl

    @Mock
    private lateinit var favoriteRepository: FavoriteRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        sut = FetchFavoriteArticleListUsecaseImpl(
            favoriteRepository,
            ArticleBusinessModelMapper(),
            ArticleListBusinessModelMapper()
        )
    }

    @Test
    fun testFetchFavoriteArticles_hasFavorite() = runTest {
        // Given
        given(favoriteRepository.getAll()).willReturn(listOf(article()))
        val model = articleListModel(true)

        // When
        val result = sut.execute(EmptyUsecaseInput).first()

        // Then
        assertEquals(model, result)
        then(favoriteRepository).should().getAll()
    }

    @Test
    fun testFetchFavoriteArticles_noFavorite() = runTest {
        // Given
        given(favoriteRepository.getAll()).willReturn(listOf())
        val model = ArticleListBusinessModel.EMPTY

        // When
        val result = sut.execute(EmptyUsecaseInput).first()

        // Then
        assertEquals(model, result)
        then(favoriteRepository).should().getAll()
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
