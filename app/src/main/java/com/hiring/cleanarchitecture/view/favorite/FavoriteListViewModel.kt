package com.hiring.cleanarchitecture.view.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hiring.cleanarchitecture.domain.model.ArticleModel
import com.hiring.cleanarchitecture.domain.usecase.favorite.FetchFavoriteArticleListUsecase
import com.hiring.cleanarchitecture.view.BaseViewModel
import com.hiring.cleanarchitecture.view.Execution
import com.hiring.cleanarchitecture.view.ViewModelArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteListViewModel @Inject constructor(
    private val fetchFavoriteArticleListUsecase: FetchFavoriteArticleListUsecase,
    viewModelArgs: ViewModelArgs
) : BaseViewModel(viewModelArgs) {

    object FetchFavoriteArticleExecution : Execution

    private val _favorites: MutableLiveData<List<ArticleModel>> = MutableLiveData()
    val favorites: LiveData<List<ArticleModel>> = _favorites

    fun fetchFavorites() {
        fetchFavoriteArticleListUsecase.execute(
            execution = FetchFavoriteArticleExecution,
            onSuccess = { _favorites.postValue(it) },
            retry = { fetchFavorites() }
        )
    }
}
