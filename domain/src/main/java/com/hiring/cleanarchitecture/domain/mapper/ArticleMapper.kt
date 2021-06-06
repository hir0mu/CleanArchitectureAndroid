package com.hiring.cleanarchitecture.domain.mapper

import com.hiring.cleanarchitecture.domain.model.ArticleModel
import com.hiring.cleanarchitecture.domain.model.UserModel
import com.hiring.data.entity.Article
import com.hiring.data.entity.User

class ArticleMapper {
    fun transform(entity: Article, isFavorite: Boolean): ArticleModel {
        return ArticleModel(
            id = entity.id,
            title = entity.title,
            url = entity.url,
            user = entity.user.mapToModel(),
            isFavorite = isFavorite
        )
    }

    private fun User.mapToModel(): UserModel {
        return UserModel(name, profileImageUrl)
    }
}
