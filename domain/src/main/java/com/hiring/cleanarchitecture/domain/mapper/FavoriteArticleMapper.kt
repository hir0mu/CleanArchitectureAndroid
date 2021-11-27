package com.hiring.cleanarchitecture.domain.mapper

import com.hiring.cleanarchitecture.domain.model.ArticleModel
import com.hiring.cleanarchitecture.domain.model.UserModel
import com.hiring.data.entity.FavArticle
import com.hiring.data.entity.User

class FavoriteArticleMapper {
    fun transform(entity: FavArticle): ArticleModel {
        return ArticleModel(
            id = entity.id,
            title = entity.title,
            url = entity.url,
            user = entity.user?.mapToModel() ?: UserModel("", "", ""),
            isFavorite = true
        )
    }

    private fun User.mapToModel(): UserModel {
        return UserModel(id, name, profileImageUrl)
    }
}
