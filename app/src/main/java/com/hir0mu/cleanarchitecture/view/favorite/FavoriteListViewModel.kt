package com.hir0mu.cleanarchitecture.view.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hir0mu.cleanarchitecture.domain.businessmodel.ArticleBusinessModel
import com.hir0mu.cleanarchitecture.domain.usecase.favorite.FetchFavoriteArticleListUsecase
import com.hir0mu.cleanarchitecture.view.BaseViewModel
import com.hir0mu.cleanarchitecture.view.Execution
import com.hir0mu.cleanarchitecture.view.ViewModelArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteListViewModel @Inject constructor(
    private val fetchFavoriteArticleListUsecase: FetchFavoriteArticleListUsecase,
    viewModelArgs: ViewModelArgs
) : BaseViewModel(viewModelArgs) {

    object FetchFavoriteArticleExecution : Execution

    private val _favorites: MutableLiveData<List<ArticleBusinessModel>> = MutableLiveData()
    val favorites: LiveData<List<ArticleBusinessModel>> = _favorites

    fun fetchFavorites() {
        fetchFavoriteArticleListUsecase.execute(
            execution = FetchFavoriteArticleExecution,
            onSuccess = { _favorites.postValue(it.articles) },
            retry = { fetchFavorites() }
        )
    }
}
