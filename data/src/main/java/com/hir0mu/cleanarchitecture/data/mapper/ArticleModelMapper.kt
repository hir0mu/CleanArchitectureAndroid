package com.hir0mu.cleanarchitecture.data.mapper

import com.hir0mu.cleanarchitecture.domain.model.ArticleModel
import com.hir0mu.cleanarchitecture.domain.model.UserModel
import com.hir0mu.cleanarchitecture.data.entity.ArticleEntity
import com.hir0mu.cleanarchitecture.data.entity.FavArticleEntity
import com.hir0mu.cleanarchitecture.data.entity.UserEntity

class ArticleModelMapper {
    fun map(entity: ArticleEntity): ArticleModel {
        return ArticleModel(
            id = entity.id,
            title = entity.title,
            url = entity.url,
            user = entity.user.mapToModel()
        )
    }

    fun map(entity: FavArticleEntity): ArticleModel {
        return ArticleModel(
            id = entity.id,
            title = entity.title,
            url = entity.url,
            user = entity.user?.mapToModel() ?: UserModel("", "", "")
        )
    }

    private fun UserEntity.mapToModel(): UserModel {
        return UserModel(id, name, profileImageUrl)
    }
}
