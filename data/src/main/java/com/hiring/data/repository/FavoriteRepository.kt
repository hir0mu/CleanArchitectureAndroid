package com.hiring.data.repository

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.Query
import com.hiring.data.entity.FavArticle

interface FavoriteRepository {
    suspend fun getAll(): List<FavArticle>

    suspend fun getArticlesByIds(articleIds: List<String>): List<FavArticle>

    fun listArticlesLiveData(): LiveData<List<FavArticle>>

    suspend fun insertAll(vararg articles: FavArticle)

    suspend fun deleteByArticleId(articleId: String)
}
