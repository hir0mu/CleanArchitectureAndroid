package com.hiring.cleanarchitecture.domain.usecase.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hiring.cleanarchitecture.domain.mapper.ArticleMapper
import com.hiring.cleanarchitecture.domain.model.ArticleModel
import com.hiring.cleanarchitecture.domain.model.merge
import com.hiring.cleanarchitecture.domain.usecase.ArticleListUsecase
import com.hiring.data.repository.ArticleRepository
import com.hiring.data.repository.FavoriteRepository

class ArticleListUsecaseImpl(
    private val articleRepository: ArticleRepository,
    private val favoriteRepository: FavoriteRepository,
    private val articleMapper: ArticleMapper
) : ArticleListUsecase {

    companion object {
        private const val PER_PAGE = 20
        private const val FIRST_PAGE = 1
    }

    private val articlesMutableLiveData: MutableLiveData<List<ArticleModel>> = MutableLiveData()
    private val allArticles: MutableList<ArticleModel> = mutableListOf()
    private var oldParams: Params? = null

    override suspend fun updateArticles(itemId: String) {

        if (oldParams?.itemId != itemId) {
            oldParams = null
        }

        val page = oldParams?.lastPage?.let { it + 1 } ?: FIRST_PAGE
        val articles = articleRepository.getArticles(itemId, page, PER_PAGE)
        val favs = favoriteRepository.getArticlesByIds(articles.map { it.id })

        val models = articles.map { entity ->
            articleMapper.transform(entity, favs.any { it.id == entity.id })
        }

        allArticles.merge(models)
        articlesMutableLiveData.postValue(allArticles)

        oldParams = Params(itemId, page)
    }

    override fun articlesLiveData(): LiveData<List<ArticleModel>> {
        return articlesMutableLiveData
    }

    private data class Params(
        val itemId: String,
        val lastPage: Int
    )
}
