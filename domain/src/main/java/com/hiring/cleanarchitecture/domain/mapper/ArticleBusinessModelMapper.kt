package com.hiring.cleanarchitecture.domain.mapper

import com.hiring.cleanarchitecture.domain.businessmodel.ArticleBusinessModel
import com.hiring.cleanarchitecture.domain.model.ArticleModel

class ArticleBusinessModelMapper {
    fun map(model: ArticleModel, isFavorite: Boolean): ArticleBusinessModel {
        return ArticleBusinessModel(
            article = model,
            isFavorite = isFavorite
        )
    }
}
