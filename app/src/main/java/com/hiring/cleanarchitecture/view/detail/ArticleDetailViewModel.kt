package com.hiring.cleanarchitecture.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hiring.cleanarchitecture.domain.businessmodel.ArticleBusinessModel
import com.hiring.cleanarchitecture.domain.model.ArticleModel
import com.hiring.cleanarchitecture.domain.usecase.article.FetchArticleDetailArgs
import com.hiring.cleanarchitecture.domain.usecase.article.FetchArticleDetailUsecase
import com.hiring.cleanarchitecture.domain.usecase.favorite.ToggleFavoriteUsecase
import com.hiring.cleanarchitecture.domain.usecase.favorite.ToggleFavoriteUsecaseArgs
import com.hiring.cleanarchitecture.view.BaseViewModel
import com.hiring.cleanarchitecture.view.Execution
import com.hiring.cleanarchitecture.view.ViewModelArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArticleDetailViewModel @Inject constructor(
    private val fetchArticleDetailUsecase: FetchArticleDetailUsecase,
    private val toggleFavoriteUsecase: ToggleFavoriteUsecase,
    viewModelArgs: ViewModelArgs
) : BaseViewModel(viewModelArgs) {

    object FetchArticleDetailExecution : Execution

    private val _article = MutableLiveData(ArticleBusinessModel.EMPTY)
    val article: LiveData<ArticleBusinessModel> = _article

    private lateinit var id: String

    fun setup(id: String) {
        this.id = id
    }

    fun fetchDetail() {
        fetchArticleDetailUsecase.execute(
            execution = FetchArticleDetailExecution,
            args = FetchArticleDetailArgs(id),
            onSuccess = { _article.value = it },
            retry = { fetchDetail() }
        )
    }

    fun toggleFavorite() {
        val old = _article.value ?: return
        toggleFavoriteUsecase.execute(
            args = ToggleFavoriteUsecaseArgs(old),
            onSuccess = {
                _article.value = old.copy(isFavorite = !old.isFavorite)
            },
            retry = { toggleFavorite() }
        )
    }
}
