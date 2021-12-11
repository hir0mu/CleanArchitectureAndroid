package com.hiring.cleanarchitecture.view.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hiring.cleanarchitecture.domain.model.ArticleModel
import com.hiring.cleanarchitecture.domain.usecase.ArticleListUsecase
import com.hiring.cleanarchitecture.view.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArticleListViewModel @Inject constructor(
    private val usecase: ArticleListUsecase
): BaseViewModel() {
    private lateinit var itemId: String

    private val _articles: MutableLiveData<List<ArticleModel>> = MutableLiveData()
    val articles: LiveData<List<ArticleModel>> = _articles

    fun setup(itemId: String) {
        this.itemId = itemId
    }

    fun fetchArticles() {
        usecase.fetchArticles(itemId)
            .execute(
                onSuccess = {
                    _articles.postValue(it)
                },
                retry = { fetchArticles() }
            )
    }

    fun toggleFavorite(article: ArticleModel) {
        usecase.toggleFavorite(article)
            .execute(
                retry = { toggleFavorite(article) }
            )
    }
}
