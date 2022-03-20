package com.hiring.cleanarchitecture.view.favorite

import com.hiring.cleanarchitecture.ViewModelTest
import com.hiring.cleanarchitecture.domain.model.ArticleModel
import com.hiring.cleanarchitecture.domain.model.UserModel
import com.hiring.cleanarchitecture.domain.usecase.UsecaseArgsUnit
import com.hiring.cleanarchitecture.domain.usecase.favorite.FetchFavoriteArticleListUsecase
import com.hiring.cleanarchitecture.testObserver
import com.hiring.cleanarchitecture.view.ViewModelArgs
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
        given(fetchFavoriteArticleListUsecase.execute(UsecaseArgsUnit))
            .willReturn(flowOf(FAVORITE_LIST))
        val testObserver = sut.favorites.testObserver()

        // When
        sut.fetchFavorites()

        // Then
        testObserver.await(count = 1)
            .shouldReceive(FAVORITE_LIST, 0)
            .end()

        verify(fetchFavoriteArticleListUsecase).execute(UsecaseArgsUnit)
    }

    @Test
    fun testFetchFavorites_Error() {
        // Given
        given(fetchFavoriteArticleListUsecase.execute(UsecaseArgsUnit))
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

        verify(fetchFavoriteArticleListUsecase).execute(UsecaseArgsUnit)
    }

    companion object {
        private val FAVORITE_LIST: List<ArticleModel> = listOf(
            ArticleModel(
                id = "id",
                title = "title",
                url = "url",
                user = UserModel(
                    id = "id",
                    name = "name",
                    profileImageUrl = "profileImageUrl"
                ),
                isFavorite = true
            )
        )
    }
}