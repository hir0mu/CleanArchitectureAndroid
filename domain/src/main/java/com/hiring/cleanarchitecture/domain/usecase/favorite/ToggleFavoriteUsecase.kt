package com.hiring.cleanarchitecture.domain.usecase.favorite

import com.hiring.cleanarchitecture.domain.businessmodel.ArticleBusinessModel
import com.hiring.cleanarchitecture.domain.businessmodel.BusinessModelUnit
import com.hiring.cleanarchitecture.domain.repository.FavoriteRepository
import com.hiring.cleanarchitecture.domain.usecase.Usecase
import com.hiring.cleanarchitecture.domain.usecase.UsecaseInput
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

typealias ToggleFavoriteUsecase = Usecase<ToggleFavoriteUsecaseInput, BusinessModelUnit>

data class ToggleFavoriteUsecaseInput(
    val businessModel: ArticleBusinessModel
) : UsecaseInput

class ToggleFavoriteUsecaseImpl(
    private val favoriteRepository: FavoriteRepository
) : ToggleFavoriteUsecase {
    override fun execute(input: ToggleFavoriteUsecaseInput): Flow<BusinessModelUnit> {
        val (businessModel) = input
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
