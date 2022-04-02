package com.hiring.cleanarchitecture.domain.usecase.favorite

import com.hiring.cleanarchitecture.domain.businessmodel.ArticleBusinessModel
import com.hiring.cleanarchitecture.domain.model.ArticleModel
import com.hiring.cleanarchitecture.domain.model.UserModel
import com.hiring.cleanarchitecture.domain.repository.FavoriteRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.*

@ExperimentalCoroutinesApi
class ToggleFavoriteUsecaseImplTest {

    private lateinit var sut: ToggleFavoriteUsecaseImpl

    @Mock
    private lateinit var favoriteRepository: FavoriteRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        sut = ToggleFavoriteUsecaseImpl(
            favoriteRepository
        )
    }

    @Test
    fun testToggleFavorite_deletion() = runTest {
        // Given
        given(favoriteRepository.deleteByArticleId(ARTICLE_ID)).willReturn(Unit)
        val model = articleModel(true)

        // When
        sut.execute(args(model)).collect()

        // Then
        then(favoriteRepository).should().deleteByArticleId(ARTICLE_ID)
        then(favoriteRepository).should(never()).insertAll(any())
    }

    @Test
    fun testToggleFavorite_addition() = runTest {
        // Given
        given(favoriteRepository.insertAll(any())).willReturn(Unit)
        val model = articleModel(false)

        // When
        sut.execute(args(model)).collect()

        // Then
        then(favoriteRepository).should().insertAll(any())
        then(favoriteRepository).should(never()).deleteByArticleId(ARTICLE_ID)
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

        private fun args(articleModel: ArticleBusinessModel) =
            ToggleFavoriteUsecaseArgs(articleModel)
    }
}
