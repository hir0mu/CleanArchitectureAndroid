package com.hiring.cleanarchitecture.domain.mapper

import com.hiring.cleanarchitecture.domain.businessmodel.ArticleBusinessModel
import com.hiring.cleanarchitecture.domain.businessmodel.ArticleListBusinessModel

class ArticleListBusinessModelMapper {
    fun map(businessModels: List<ArticleBusinessModel>): ArticleListBusinessModel {
        return ArticleListBusinessModel(
            articles = businessModels
        )
    }
}
