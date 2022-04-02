package com.hiring.data.mapper

import com.hiring.cleanarchitecture.domain.model.ArticleModel
import com.hiring.cleanarchitecture.domain.model.UserModel
import com.hiring.data.entity.ArticleEntity
import com.hiring.data.entity.FavArticleEntity
import com.hiring.data.entity.UserEntity

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
