package com.hir0mu.cleanarchitecture.data.api.local

import com.hir0mu.cleanarchitecture.data.api.ArticleApi
import com.hir0mu.cleanarchitecture.data.entity.ArticleEntity
import com.hir0mu.cleanarchitecture.data.entity.UserEntity
import kotlinx.coroutines.delay

class ArticleLocal : ArticleApi {
    private val colors = listOf(
        "ffcfdf",
        "fefdca",
        "e0f9b5",
        "a5dee5",
        "ebfffa",
        "c6fce5",
        "6ef3d6",
        "0dceda",
        "f9ed69",
        "f08a5d",
        "b83b5e",
        "6a2c70",
    )

    override suspend fun articles(itemId: String, page: Int, perPage: Int): List<ArticleEntity> {
        delay(1000)
        val firstPage = (page - 1) * perPage
        val lastPage = page * perPage
        return (firstPage..lastPage).map {
            val color = colors[it % colors.size]
            createArticleEntity(
                id = it.toString(),
                title = "${itemId}の記事 $it",
                userId = it.toString(),
                userName = "ユーザネーム $it",
                profileImageUrl = "https://dummyimage.com/400x400/$color/fff"
            )
        }
    }

    override suspend fun articleDetail(id: String): ArticleEntity {
        delay(1000)
        val color = colors[(0..colors.lastIndex).random()]
        return createArticleEntity(
            id = id,
            title = "記事 $id",
            userId = id,
            userName = "ユーザネーム $id",
            profileImageUrl = "https://dummyimage.com/400x400/$color/fff"
        )
    }

    private fun createArticleEntity(
        id: String,
        title: String,
        userId: String,
        userName: String,
        profileImageUrl: String
    ): ArticleEntity {
        return ArticleEntity(
            renderedBody = "",
            body = "",
            coediting = false,
            commentsCount = 0,
            createdAt = "",
            group = null,
            id = id,
            likesCount = 0,
            private = false,
            reactionsCount = 0,
            tags = emptyList(),
            title = title,
            updatedAt = "",
            url = "https://www.google.com/",
            user = UserEntity(
                description = "",
                facebookId = "",
                followeesCount = 0,
                followersCount = 0,
                githubLoginName = "",
                id = userId,
                itemsCount = 0,
                linkedinId = "",
                location = "",
                name = userName,
                organization = "",
                permanentId = 0,
                profileImageUrl = profileImageUrl,
                teamOnly = false,
                twitterScreenName = "",
                websiteUrl = ""
            ),
            pageViewsCount = 0
        )
    }
}
