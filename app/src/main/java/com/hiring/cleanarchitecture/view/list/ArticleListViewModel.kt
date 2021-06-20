package com.hiring.cleanarchitecture.view.list

import androidx.lifecycle.LiveData
import com.hiring.cleanarchitecture.domain.model.ArticleModel
import com.hiring.cleanarchitecture.domain.usecase.ArticleListUsecase
import com.hiring.cleanarchitecture.view.BaseViewModel

class ArticleListViewModel(
    private val usecase: ArticleListUsecase
): BaseViewModel() {
    private lateinit var itemId: String

    val articles: LiveData<List<ArticleModel>> by lazy { usecase.articlesLiveData() }

    fun setup(itemId: String) {
        this.itemId = itemId
    }

    fun fetchArticles() {
        execute {
            usecase.updateArticles(itemId)
        }
    }
}
