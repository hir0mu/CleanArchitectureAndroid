package com.hiring.cleanarchitecture.domain.usecase.favorite

import com.hiring.cleanarchitecture.domain.businessmodel.ArticleBusinessModel
import com.hiring.cleanarchitecture.domain.businessmodel.BusinessModelUnit
import com.hiring.cleanarchitecture.domain.repository.FavoriteRepository
import com.hiring.cleanarchitecture.domain.usecase.Usecase
import com.hiring.cleanarchitecture.domain.usecase.UsecaseArgs
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

typealias ToggleFavoriteUsecase = Usecase<ToggleFavoriteUsecaseArgs, BusinessModelUnit>

data class ToggleFavoriteUsecaseArgs(
    val businessModel: ArticleBusinessModel
) : UsecaseArgs

class ToggleFavoriteUsecaseImpl(
    private val favoriteRepository: FavoriteRepository
) : ToggleFavoriteUsecase {
    override fun execute(args: ToggleFavoriteUsecaseArgs): Flow<BusinessModelUnit> {
        val (businessModel) = args
        return flow {
            if (businessModel.isFavorite) {
                favoriteRepository.deleteByArticleId(businessModel.article.id)
            } else {
                favoriteRepository.insertAll(listOf(businessModel.article))
            }
            emit(BusinessModelUnit)
        }
    }
}
