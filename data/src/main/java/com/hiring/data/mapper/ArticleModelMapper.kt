package com.hiring.data.mapper

import com.hiring.cleanarchitecture.domain.model.ArticleModel
import com.hiring.cleanarchitecture.domain.model.UserModel
import com.hiring.data.entity.Article
import com.hiring.data.entity.FavArticle
import com.hiring.data.entity.User

class ArticleModelMapper {
    fun map(entity: Article): ArticleModel {
        return ArticleModel(
            id = entity.id,
            title = entity.title,
            url = entity.url,
            user = entity.user.mapToModel()
        )
    }

    fun map(entity: FavArticle): ArticleModel {
        return ArticleModel(
            id = entity.id,
            title = entity.title,
            url = entity.url,
            user = entity.user?.mapToModel() ?: UserModel("", "", "")
        )
    }

    private fun User.mapToModel(): UserModel {
        return UserModel(id, name, profileImageUrl)
    }
}
