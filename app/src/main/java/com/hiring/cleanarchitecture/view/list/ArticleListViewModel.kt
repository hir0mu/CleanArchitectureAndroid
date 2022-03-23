package com.hiring.cleanarchitecture.view.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hiring.cleanarchitecture.domain.businessmodel.ArticleBusinessModel
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
    private val fetchArticleListUsecase: FetchArticleListUsecase,
    private val toggleFavoriteUsecase: ToggleFavoriteUsecase,
    viewModelArgs: ViewModelArgs
) : BaseViewModel(viewModelArgs) {
    companion object {
        private const val FIRST_PAGE = 1
    }

    object FetchArticleListExecution : Execution

    private val _articles: MutableLiveData<List<ArticleBusinessModel>> = MutableLiveData()
    val articles: LiveData<List<ArticleBusinessModel>> = _articles

    val searchQuery: MutableLiveData<String> = MutableLiveData("android")

    private var params = SearchParams.EMPTY
    private var isLoading = false

    val articleCount: Int?
        get() = articles.value?.size

    fun setup() {
        if (params.isEmpty()) {
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

        fetchArticleListUsecase.execute(
            execution = FetchArticleListExecution,
            args = FetchArticleListArgs(params.itemId, params.page),
            onSuccess = {
                val old = if (params.page == FIRST_PAGE) listOf() else _articles.value.orEmpty()
                _articles.postValue(old.merged(it.articles))
                params = params.countedUp()
                isLoading = false
            },
            retry = {
                isLoading = false
                fetchArticles(shouldReset)
            }
        )
    }

    fun toggleFavorite(article: ArticleBusinessModel) {
        toggleFavoriteUsecase.execute(
            args = ToggleFavoriteUsecaseArgs(article),
            retry = { toggleFavorite(article) }
        )
    }
}

private fun SearchParams.countedUp(): SearchParams {
    return copy(page = page + 1)
}

private fun List<ArticleBusinessModel>.merged(newItems: List<ArticleBusinessModel>): List<ArticleBusinessModel> {
    val ids = this.map { it.article.id }
    val self = this.toMutableList()
    newItems.forEach { new ->
        if (!ids.contains(new.article.id)) {
            self.add(new)
        }
    }
    return self
}
