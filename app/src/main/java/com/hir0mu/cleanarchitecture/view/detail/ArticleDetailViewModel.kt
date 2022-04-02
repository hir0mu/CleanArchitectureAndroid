package com.hir0mu.cleanarchitecture.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hir0mu.cleanarchitecture.domain.businessmodel.ArticleBusinessModel
import com.hir0mu.cleanarchitecture.domain.usecase.article.FetchArticleDetailInput
import com.hir0mu.cleanarchitecture.domain.usecase.article.FetchArticleDetailUsecase
import com.hir0mu.cleanarchitecture.domain.usecase.favorite.ToggleFavoriteUsecase
import com.hir0mu.cleanarchitecture.domain.usecase.favorite.ToggleFavoriteUsecaseInput
import com.hir0mu.cleanarchitecture.view.BaseViewModel
import com.hir0mu.cleanarchitecture.view.Execution
import com.hir0mu.cleanarchitecture.view.ViewModelArgs
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
            args = FetchArticleDetailInput(id),
            onSuccess = { _article.value = it },
            retry = { fetchDetail() }
        )
    }

    fun toggleFavorite() {
        val old = _article.value ?: return
        toggleFavoriteUsecase.execute(
            args = ToggleFavoriteUsecaseInput(old),
            onSuccess = {
                _article.value = old.copy(isFavorite = !old.isFavorite)
            },
            retry = { toggleFavorite() }
        )
    }
}
