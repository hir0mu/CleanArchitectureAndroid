package com.hir0mu.cleanarchitecture.view.favorite

import com.hir0mu.cleanarchitecture.ViewModelTest
import com.hir0mu.cleanarchitecture.domain.businessmodel.ArticleBusinessModel
import com.hir0mu.cleanarchitecture.domain.businessmodel.ArticleListBusinessModel
import com.hir0mu.cleanarchitecture.domain.model.ArticleModel
import com.hir0mu.cleanarchitecture.domain.model.UserModel
import com.hir0mu.cleanarchitecture.domain.usecase.EmptyUsecaseInput
import com.hir0mu.cleanarchitecture.domain.usecase.favorite.FetchFavoriteArticleListUsecase
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
class FavoriteListViewModelTest : ViewModelTest() {

    private lateinit var sut: FavoriteListViewModel

    @Mock
    private lateinit var fetchFavoriteArticleListUsecase: FetchFavoriteArticleListUsecase

    override fun setup() {
        sut = FavoriteListViewModel(
            fetchFavoriteArticleListUsecase = fetchFavoriteArticleListUsecase,
            viewModelArgs = ViewModelArgs(UnconfinedTestDispatcher(TestCoroutineScheduler()))
        )
    }

    @Test
    fun testFetchFavorites() {
        // Given
        given(fetchFavoriteArticleListUsecase.execute(EmptyUsecaseInput))
            .willReturn(flowOf(FAVORITE_LIST))
        val testObserver = sut.favorites.testObserver()

        // When
        sut.fetchFavorites()

        // Then
        testObserver.await(count = 1)
            .shouldReceive(FAVORITE_LIST.articles, 0)
            .end()

        verify(fetchFavoriteArticleListUsecase).execute(EmptyUsecaseInput)
    }

    @Test
    fun testFetchFavorites_Error() {
        // Given
        given(fetchFavoriteArticleListUsecase.execute(EmptyUsecaseInput))
            .willReturn(flowOfException())
        val testObserver = sut.favorites.testObserver()
        val errorObserver = sut.error.testObserver()

        // When
        sut.fetchFavorites()

        // Then
        testObserver.await(count = 0)
            .withValueCount(0)
            .end()

        errorObserver.await(count = 1)
            .withValueCount(1)
            .end()

        verify(fetchFavoriteArticleListUsecase).execute(EmptyUsecaseInput)
    }

    companion object {
        private val FAVORITE_LIST = ArticleListBusinessModel(
            articles = listOf(
                ArticleBusinessModel(
                    article = ArticleModel(
                        id = "id",
                        title = "title",
                        url = "url",
                        user = UserModel(
                            id = "id",
                            name = "name",
                            profileImageUrl = "profileImageUrl"
                        )
                    ),
                    isFavorite = true
                )
            )
        )
    }
}