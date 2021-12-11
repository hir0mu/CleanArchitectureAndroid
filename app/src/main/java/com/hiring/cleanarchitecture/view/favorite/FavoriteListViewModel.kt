package com.hiring.cleanarchitecture.view.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hiring.cleanarchitecture.domain.model.ArticleModel
import com.hiring.cleanarchitecture.domain.usecase.FavoriteArticleUsecase
import com.hiring.cleanarchitecture.view.BaseViewModel

class FavoriteListViewModel(
    private val usecase: FavoriteArticleUsecase
): BaseViewModel() {
    private val _favorites: MutableLiveData<List<ArticleModel>> = MutableLiveData()
    val favorites: LiveData<List<ArticleModel>> = _favorites

    fun fetchFavorites() {
        usecase.fetchFavoriteArticles()
            .execute(
                onSuccess = { _favorites.postValue(it) },
                retry = { fetchFavorites() }
            )
    }
}
