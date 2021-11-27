package com.hiring.cleanarchitecture.view.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hiring.cleanarchitecture.domain.model.ArticleModel
import com.hiring.cleanarchitecture.domain.usecase.ArticleListUsecase
import com.hiring.cleanarchitecture.view.BaseViewModel

class ArticleListViewModel(
    private val usecase: ArticleListUsecase
): BaseViewModel() {
    private lateinit var itemId: String

    private val _articles: MutableLiveData<List<ArticleModel>> = MutableLiveData()
    val articles: LiveData<List<ArticleModel>> = _articles

    fun setup(itemId: String) {
        this.itemId = itemId
    }

    fun fetchArticles() {
        execute {
            val articles = usecase.fetchArticles(itemId)
            _articles.postValue(articles)
        }
    }

    fun toggleFavorite(article: ArticleModel) {
        execute { usecase.toggleFavorite(article) }
    }
}
