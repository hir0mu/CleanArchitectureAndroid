package com.hiring.cleanarchitecture.view.list

import com.hiring.cleanarchitecture.ViewModelTest
import com.hiring.cleanarchitecture.domain.businessmodel.ArticleBusinessModel
import com.hiring.cleanarchitecture.domain.businessmodel.ArticleListBusinessModel
import com.hiring.cleanarchitecture.domain.businessmodel.BusinessModelUnit
import com.hiring.cleanarchitecture.domain.model.ArticleModel
import com.hiring.cleanarchitecture.domain.model.UserModel
import com.hiring.cleanarchitecture.domain.usecase.article.FetchArticleListInput
import com.hiring.cleanarchitecture.domain.usecase.article.FetchArticleListUsecase
import com.hiring.cleanarchitecture.domain.usecase.favorite.ToggleFavoriteUsecase
import com.hiring.cleanarchitecture.domain.usecase.favorite.ToggleFavoriteUsecaseInput
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
class ArticleListViewModelTest : ViewModelTest() {

    private lateinit var sut: ArticleListViewModel

    @Mock
    private lateinit var fetchArticleListUsecase: FetchArticleListUsecase

    @Mock
    private lateinit var toggleFavoriteUsecase: ToggleFavoriteUsecase

    override fun setup() {
        sut = ArticleListViewModel(
            fetchArticleListUsecase = fetchArticleListUsecase,
            toggleFavoriteUsecase = toggleFavoriteUsecase,
            viewModelArgs = ViewModelArgs(UnconfinedTestDispatcher(TestCoroutineScheduler()))
        )
    }

    @Test
    fun testSetup() {
        // Given
        val itemId = sut.searchQuery.value.orEmpty()
        given(fetchArticleListUsecase.execute(fetchArticleListArgs(itemId, 1)))
            .willReturn(flowOf(ARTICLE_LIST_1))
        val testObserver = sut.articles.testObserver()

        // When
        sut.setup()

        // Then
        testObserver.await()
            .shouldReceive(ARTICLE_LIST_1.articles)
            .end()

        verify(fetchArticleListUsecase).execute(fetchArticleListArgs(itemId, 1))
    }

    @Test
    fun testSetup_Error() {
        // Given
        val itemId = sut.searchQuery.value.orEmpty()
        given(fetchArticleListUsecase.execute(fetchArticleListArgs(itemId, 1)))
            .willReturn(flowOfException())
        val testObserver = sut.articles.testObserver()
        val errorObserver = sut.error.testObserver()

        // When
        sut.setup()

        // Then
        testObserver.await(count = 0)
            .withValueCount(0)
            .end()

        errorObserver.await(count = 1)
            .withValueCount(1)
            .end()

        verify(fetchArticleListUsecase).execute(fetchArticleListArgs(itemId, 1))
    }

    @Test
    fun testFetchArticles_Paging() {
        // Given
        val itemId = sut.searchQuery.value.orEmpty()
        given(fetchArticleListUsecase.execute(fetchArticleListArgs(itemId, 1)))
            .willReturn(flowOf(ARTICLE_LIST_1))
        given(fetchArticleListUsecase.execute(fetchArticleListArgs(itemId, 2)))
            .willReturn(flowOf(ARTICLE_LIST_2))
        val testObserver = sut.articles.testObserver()

        // When
        sut.setup()
        sut.fetchArticles(shouldReset = false)

        // Then
        testObserver.await(count = 2)
            .shouldReceive(ARTICLE_LIST_1.articles, 0)
            .shouldReceive(ARTICLE_LIST_ALL, 1)
            .withValueCount(2)
            .end()

        verify(fetchArticleListUsecase).execute(fetchArticleListArgs(itemId, 1))
        verify(fetchArticleListUsecase).execute(fetchArticleListArgs(itemId, 2))
    }

    @Test
    fun testFetchArticles_Paging_Error() {
        // Given
        val itemId = sut.searchQuery.value.orEmpty()
        given(fetchArticleListUsecase.execute(fetchArticleListArgs(itemId, 1)))
            .willReturn(flowOf(ARTICLE_LIST_1))
        given(fetchArticleListUsecase.execute(fetchArticleListArgs(itemId, 2)))
            .willReturn(flowOfException())
        val testObserver = sut.articles.testObserver()
        val errorObserver = sut.error.testObserver()

        // When
        sut.setup()
        sut.fetchArticles(shouldReset = false)

        // Then
        testObserver.await(count = 1)
            .shouldReceive(ARTICLE_LIST_1.articles, 0)
            .withValueCount(1)
            .end()

        errorObserver.await(count = 1)
            .withValueCount(1)
            .end()

        verify(fetchArticleListUsecase).execute(fetchArticleListArgs(itemId, 1))
        verify(fetchArticleListUsecase).execute(fetchArticleListArgs(itemId, 2))
    }

    @Test
    fun testToggleFavorite() {
        // Given
        val itemId = sut.searchQuery.value.orEmpty()
        given(fetchArticleListUsecase.execute(fetchArticleListArgs(itemId, 1)))
            .willReturn(flowOf(ARTICLE_LIST_1))
        given(toggleFavoriteUsecase.execute(toggleFavoriteArgs))
            .willReturn(flowOf(BusinessModelUnit))
        val testObserver = sut.articles.testObserver()

        // When
        sut.setup()
        sut.toggleFavorite(ARTICLE_MODEL_1)

        // Then
        testObserver.await(count = 1).end()
        verify(toggleFavoriteUsecase).execute(toggleFavoriteArgs)
    }

    @Test
    fun testToggleFavorite_Error() {
        // Given
        val itemId = sut.searchQuery.value.orEmpty()
        given(fetchArticleListUsecase.execute(fetchArticleListArgs(itemId, 1)))
            .willReturn(flowOf(ARTICLE_LIST_1))
        given(toggleFavoriteUsecase.execute(toggleFavoriteArgs))
            .willReturn(flowOfException())
        val testObserver = sut.articles.testObserver()
        val errorObserver = sut.error.testObserver()

        // When
        sut.setup()
        sut.toggleFavorite(ARTICLE_MODEL_1)

        // Then
        testObserver.await(count = 1).end()

        errorObserver.await(count = 1)
            .withValueCount(1)
            .end()

        verify(toggleFavoriteUsecase).execute(toggleFavoriteArgs)
    }

    companion object {
        private val ARTICLE_MODEL_1 = ArticleBusinessModel(
            article = ArticleModel(
                id = "id 1",
                title = "title 1",
                url = "url 1",
                user = UserModel(
                    id = "id 1",
                    name = "name 1",
                    profileImageUrl = "profileImageUrl 1"
                ),
            ),
            isFavorite = true
        )

        private val ARTICLE_MODEL_2 = ArticleBusinessModel(
            article = ArticleModel(
                id = "id 2",
                title = "title 2",
                url = "url 2",
                user = UserModel(
                    id = "id 2",
                    name = "name 2",
                    profileImageUrl = "profileImageUrl 2"
                ),
            ),
            isFavorite = true
        )

        private val ARTICLE_LIST_1 = ArticleListBusinessModel(articles = listOf(ARTICLE_MODEL_1))
        private val ARTICLE_LIST_2 = ArticleListBusinessModel(articles = listOf(ARTICLE_MODEL_2))
        private val ARTICLE_LIST_ALL: List<ArticleBusinessModel> =
            listOf(ARTICLE_MODEL_1, ARTICLE_MODEL_2)

        private val toggleFavoriteArgs = ToggleFavoriteUsecaseInput(ARTICLE_MODEL_1)

        fun fetchArticleListArgs(itemId: String, page: Int) = FetchArticleListInput(
            itemId = itemId,
            page = page
        )
    }
}
