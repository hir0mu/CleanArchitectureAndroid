package com.hir0mu.cleanarchitecture.view.detail

import com.hir0mu.cleanarchitecture.ViewModelTest
import com.hir0mu.cleanarchitecture.domain.businessmodel.ArticleBusinessModel
import com.hir0mu.cleanarchitecture.domain.businessmodel.BusinessModelUnit
import com.hir0mu.cleanarchitecture.domain.model.ArticleModel
import com.hir0mu.cleanarchitecture.domain.model.UserModel
import com.hir0mu.cleanarchitecture.domain.usecase.article.FetchArticleDetailInput
import com.hir0mu.cleanarchitecture.domain.usecase.article.FetchArticleDetailUsecase
import com.hir0mu.cleanarchitecture.domain.usecase.favorite.ToggleFavoriteUsecase
import com.hir0mu.cleanarchitecture.domain.usecase.favorite.ToggleFavoriteUsecaseInput
import com.hir0mu.cleanarchitecture.testObserver
import com.hir0mu.cleanarchitecture.view.ViewModelArgs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Test
import org.mockito.Mock
import org.mockito.kotlin.given
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class ArticleDetailViewModelTest : ViewModelTest() {

    private lateinit var sut: ArticleDetailViewModel

    @Mock
    private lateinit var fetchArticleDetailUsecase: FetchArticleDetailUsecase

    @Mock
    private lateinit var toggleFavoriteUsecase: ToggleFavoriteUsecase

    override fun setup() {
        sut = ArticleDetailViewModel(
            fetchArticleDetailUsecase,
            toggleFavoriteUsecase,
            ViewModelArgs(UnconfinedTestDispatcher(TestCoroutineScheduler()))
        )
    }

    @Test
    fun testFetchDetail() {
        // Given
        given(fetchArticleDetailUsecase.execute(fetchArticleDetailArgs))
            .willReturn(flowOf(ARTICLE_BUSINESS_MODEL))
        val testObserver = sut.article.testObserver()
        sut.setup(ID)

        // When
        sut.fetchDetail()

        // Then
        testObserver.await(count = 2)
            .shouldReceive(ArticleBusinessModel.EMPTY, 0)
            .shouldReceive(ARTICLE_BUSINESS_MODEL, 1)
            .end()

        verify(fetchArticleDetailUsecase).execute(fetchArticleDetailArgs)
    }

    @Test
    fun testFetchDetail_Error() {
        // Given
        given(fetchArticleDetailUsecase.execute(fetchArticleDetailArgs))
            .willReturn(flowOfException())
        val testObserver = sut.article.testObserver()
        val errorObserver = sut.error.testObserver()
        sut.setup(ID)

        // When
        sut.fetchDetail()

        // Then
        testObserver.await(count = 1)
            .shouldReceive(ArticleBusinessModel.EMPTY, 0)
            .withValueCount(1)
            .end()

        errorObserver.await(count = 1)
            .withValueCount(1)
            .end()

        verify(fetchArticleDetailUsecase).execute(fetchArticleDetailArgs)
    }

    @Test
    fun testToggleFavorite() {
        // Given
        given(fetchArticleDetailUsecase.execute(fetchArticleDetailArgs))
            .willReturn(flowOf(ARTICLE_BUSINESS_MODEL))
        given(toggleFavoriteUsecase.execute(toggleFavoriteArgs))
            .willReturn(flowOf(BusinessModelUnit))
        val testObserver = sut.article.testObserver()
        sut.setup(ID)
        sut.fetchDetail()

        // When
        sut.toggleFavorite()

        // Then
        testObserver.await(count = 2)
        verify(toggleFavoriteUsecase).execute(toggleFavoriteArgs)
    }

    @Test
    fun testToggleFavorite_Error() {
        // Given
        given(fetchArticleDetailUsecase.execute(fetchArticleDetailArgs))
            .willReturn(flowOf(ARTICLE_BUSINESS_MODEL))
        given(toggleFavoriteUsecase.execute(toggleFavoriteArgs))
            .willReturn(flowOfException())
        val testObserver = sut.article.testObserver()
        val errorObserver = sut.error.testObserver()
        sut.setup(ID)
        sut.fetchDetail()

        // When
        sut.toggleFavorite()

        // Then
        testObserver.await(count = 2)
        errorObserver.await(count = 1)
            .withValueCount(1)
            .end()
        verify(toggleFavoriteUsecase).execute(toggleFavoriteArgs)
    }

    companion object {
        private const val ID = "id"
        private val ARTICLE_BUSINESS_MODEL = ArticleBusinessModel(
            article = ArticleModel(
                id = ID,
                title = "title",
                url = "url",
                user = UserModel(
                    id = "id",
                    name = "name",
                    profileImageUrl = "profileImageUrl"
                ),
            ),
            isFavorite = false
        )

        private val fetchArticleDetailArgs = FetchArticleDetailInput(id = ID)
        private val toggleFavoriteArgs = ToggleFavoriteUsecaseInput(ARTICLE_BUSINESS_MODEL)
    }
}
