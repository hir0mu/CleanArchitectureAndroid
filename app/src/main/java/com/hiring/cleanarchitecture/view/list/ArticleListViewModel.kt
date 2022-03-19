package com.hiring.cleanarchitecture.view.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hiring.cleanarchitecture.domain.model.ArticleModel
import com.hiring.cleanarchitecture.domain.usecase.article.FetchArticleListArgs
import com.hiring.cleanarchitecture.domain.usecase.article.FetchArticleListUsecase
import com.hiring.cleanarchitecture.domain.usecase.favorite.ToggleFavoriteUsecase
import com.hiring.cleanarchitecture.domain.usecase.favorite.ToggleFavoriteUsecaseArgs
import com.hiring.cleanarchitecture.view.BaseViewModel
import com.hiring.cleanarchitecture.view.ViewModelArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ArticleListViewModel @Inject constructor(
    private val usecase: FetchArticleListUsecase,
    private val toggleUsecase: ToggleFavoriteUsecase,
    viewModelArgs: ViewModelArgs
) : BaseViewModel(viewModelArgs) {
    companion object {
        private const val FIRST_PAGE = 1
    }

    private var params = SearchParams.EMPTY

    private val _articles: MutableLiveData<List<ArticleModel>> = MutableLiveData()
    val articles: LiveData<List<ArticleModel>> = _articles

    fun setup(itemId: String) {
        params = SearchParams(itemId = itemId, page = FIRST_PAGE)
    }

    fun fetchArticles() {
        val old = if (params.page == FIRST_PAGE) listOf() else _articles.value.orEmpty()

        usecase.execute(
            args = FetchArticleListArgs(params.itemId, params.page),
            onSuccess = {
                _articles.postValue(old.merged(it))
                params = params.countedUp()
            },
            retry = { fetchArticles() }
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
