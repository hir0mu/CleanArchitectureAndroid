package com.hiring.cleanarchitecture.domain.usecase

import androidx.lifecycle.LiveData
import com.hiring.cleanarchitecture.domain.model.ArticleModel

interface ArticleListUsecase {
    suspend fun updateArticles(itemId: String)

    fun articlesLiveData(): LiveData<List<ArticleModel>>
}
