package com.hiring.cleanarchitecture.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hiring.cleanarchitecture.domain.model.ArticleModel
import com.hiring.cleanarchitecture.domain.usecase.article.FetchArticleDetailUsecase
import com.hiring.cleanarchitecture.domain.usecase.favorite.ToggleFavoriteUsecase
import com.hiring.cleanarchitecture.view.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArticleDetailViewModel @Inject constructor(
    private val detailUsecase: FetchArticleDetailUsecase,
    private val toggleFavoriteUsecase: ToggleFavoriteUsecase
): BaseViewModel() {
    private val _article: MutableLiveData<ArticleModel> = MutableLiveData(ArticleModel.EMPTY)
    val article: LiveData<ArticleModel> = _article

    private lateinit var id: String

    fun setup(id: String) {
        this.id = id
    }

    fun fetchDetail() {
        detailUsecase.fetchArticle(id)
            .execute(
                onSuccess = { _article.value = it },
                retry = { fetchDetail() }
            )
    }

    fun toggleFavorite() {
        val old = _article.value ?: return
        toggleFavoriteUsecase.toggleFavorite(old)
            .execute(
                onSuccess = {
                    _article.value = old.copy(isFavorite = !old.isFavorite)
                },
                retry = { toggleFavorite() }
            )
    }
}
