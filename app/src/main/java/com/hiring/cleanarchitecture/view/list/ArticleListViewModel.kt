package com.hiring.cleanarchitecture.view.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hiring.cleanarchitecture.domain.model.ArticleModel
import com.hiring.cleanarchitecture.domain.usecase.article.FetchArticleListArgs
import com.hiring.cleanarchitecture.domain.usecase.article.FetchArticleListUsecase
import com.hiring.cleanarchitecture.domain.usecase.favorite.ToggleFavoriteUsecase
import com.hiring.cleanarchitecture.domain.usecase.favorite.ToggleFavoriteUsecaseArgs
import com.hiring.cleanarchitecture.view.BaseViewModel
import com.hiring.cleanarchitecture.view.Execution
import com.hiring.cleanarchitecture.view.ViewModelArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArticleListViewModel @Inject constructor(
    private val usecase: FetchArticleListUsecase,
    private val toggleUsecase: ToggleFavoriteUsecase,
    viewModelArgs: ViewModelArgs
) : BaseViewModel(viewModelArgs) {
    companion object {
        private const val FIRST_PAGE = 1
    }

    object FetchArticleListExecution : Execution

    private val _articles: MutableLiveData<List<ArticleModel>> = MutableLiveData()
    val articles: LiveData<List<ArticleModel>> = _articles

    val searchQuery: MutableLiveData<String> = MutableLiveData("android")

    private var params = SearchParams.EMPTY
    private var isLoading = false

    val articleCount: Int?
        get() = articles.value?.size

    fun setup() {
        if (params == SearchParams.EMPTY) {
            params = SearchParams(itemId = searchQuery.value.orEmpty(), page = FIRST_PAGE)
            fetchArticles()
        }
    }

    fun fetchArticles(shouldReset: Boolean = false) {
        if (isLoading) {
            return
        }
        isLoading = true

        if (shouldReset) {
            params = SearchParams(itemId = searchQuery.value.orEmpty(), page = FIRST_PAGE)
            _articles.value = listOf()
        }

        usecase.execute(
            execution = FetchArticleListExecution,
            args = FetchArticleListArgs(params.itemId, params.page),
            onSuccess = {
                val old = if (params.page == FIRST_PAGE) listOf() else _articles.value.orEmpty()
                _articles.postValue(old.merged(it))
                params = params.countedUp()
                isLoading = false
            },
            retry = {
                isLoading = false
                fetchArticles(shouldReset)
            }
        )
    }

    fun toggleFavorite(article: ArticleModel) {
        toggleUsecase.execute(
            args = ToggleFavoriteUsecaseArgs(article),
            retry = { toggleFavorite(article) }
        )
    }
}

private fun SearchParams.countedUp(): SearchParams {
    return copy(page = page + 1)
}

private fun List<ArticleModel>.merged(newItems: List<ArticleModel>): List<ArticleModel> {
    val ids = this.map { it.id }
    val self = this.toMutableList()
    newItems.forEach { new ->
        if (!ids.contains(new.id)) {
            self.add(new)
        }
    }
    return self
}
