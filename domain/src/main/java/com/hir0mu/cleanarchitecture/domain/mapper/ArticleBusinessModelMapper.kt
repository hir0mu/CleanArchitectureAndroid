package com.hir0mu.cleanarchitecture.domain.mapper

import com.hir0mu.cleanarchitecture.domain.businessmodel.ArticleBusinessModel
import com.hir0mu.cleanarchitecture.domain.model.ArticleModel

class ArticleBusinessModelMapper {
    fun map(model: ArticleModel, isFavorite: Boolean): ArticleBusinessModel {
        return ArticleBusinessModel(
            article = model,
            isFavorite = isFavorite
        )
    }
}
