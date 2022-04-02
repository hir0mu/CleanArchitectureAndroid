package com.hir0mu.cleanarchitecture.domain.usecase.article

import com.hir0mu.cleanarchitecture.domain.businessmodel.ArticleListBusinessModel
import com.hir0mu.cleanarchitecture.domain.mapper.ArticleBusinessModelMapper
import com.hir0mu.cleanarchitecture.domain.mapper.ArticleListBusinessModelMapper
import com.hir0mu.cleanarchitecture.domain.repository.ArticleRepository
import com.hir0mu.cleanarchitecture.domain.repository.FavoriteRepository
import com.hir0mu.cleanarchitecture.domain.usecase.Usecase
import com.hir0mu.cleanarchitecture.domain.usecase.UsecaseInput
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

typealias FetchArticleListUsecase = Usecase<FetchArticleListInput, ArticleListBusinessModel>

data class FetchArticleListInput(
    val itemId: String,
    val page: Int
) : UsecaseInput

class FetchArticleListUsecaseImpl(
    private val articleRepository: ArticleRepository,
    private val favoriteRepository: FavoriteRepository,
    private val articleMapper: ArticleBusinessModelMapper,
    private val articleListMapper: ArticleListBusinessModelMapper
) : FetchArticleListUsecase {

    companion object {
        private const val PER_PAGE = 20
    }

    override fun execute(input: FetchArticleListInput): Flow<ArticleListBusinessModel> {
        val (itemId, page) = input
        return flow {
            val articles = articleRepository.getArticles(itemId, page, PER_PAGE)
            val favs = favoriteRepository.getArticlesByIds(articles.map { it.id })
                .map { it.id }

            val models = articles.map { model ->
                articleMapper.map(model, favs.any { model.id == it })
            }.let { articleListMapper.map(it) }

            emit(models)
        }
    }
}
