package com.hir0mu.cleanarchitecture.domain.mapper

import com.hir0mu.cleanarchitecture.domain.businessmodel.ArticleBusinessModel
import com.hir0mu.cleanarchitecture.domain.businessmodel.ArticleListBusinessModel

class ArticleListBusinessModelMapper {
    fun map(businessModels: List<ArticleBusinessModel>): ArticleListBusinessModel {
        return ArticleListBusinessModel(
            articles = businessModels
        )
    }
}
