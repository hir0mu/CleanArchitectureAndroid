package com.hiring.cleanarchitecture.domain.usecase.article

import com.hiring.cleanarchitecture.domain.mapper.ArticleMapper
import com.hiring.cleanarchitecture.domain.model.ArticleModel
import com.hiring.cleanarchitecture.domain.usecase.Usecase
import com.hiring.cleanarchitecture.domain.usecase.UsecaseArgs
import com.hiring.data.repository.ArticleRepository
import com.hiring.data.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

typealias FetchArticleListUsecase = Usecase<FetchArticleListArgs, List<ArticleModel>>

data class FetchArticleListArgs(
    val itemId: String,
    val page: Int
) : UsecaseArgs

class FetchArticleListUsecaseImpl(
    private val articleRepository: ArticleRepository,
    private val favoriteRepository: FavoriteRepository,
    private val articleMapper: ArticleMapper
) : FetchArticleListUsecase {

    companion object {
        private const val PER_PAGE = 20
    }

    override fun execute(args: FetchArticleListArgs): Flow<List<ArticleModel>> {
        val (itemId, page) = args
        return flow {
            val articles = articleRepository.getArticles(itemId, page, PER_PAGE)
            val favs = favoriteRepository.getArticlesByIds(articles.map { it.id })

            val models = articles.map { entity ->
                articleMapper.transform(entity, favs.any { it.id == entity.id })
            }

            emit(models)
        }
    }
}
