package com.hiring.cleanarchitecture.domain.usecase

import androidx.lifecycle.LiveData
import com.hiring.cleanarchitecture.domain.model.ArticleModel

interface FavoriteArticleUsecase {
    fun favoriteArticlesLiveData(): LiveData<List<ArticleModel>>
}
