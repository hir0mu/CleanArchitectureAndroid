package com.hiring.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.hiring.data.entity.FavArticle

@Dao
interface ArticleDao {
    @Query("SELECT * FROM fav_article")
    fun getAll(): List<FavArticle>

    @Query("SELECT * FROM fav_article")
    fun listMembersLiveData(): LiveData<List<FavArticle>>

    @Insert
    fun insertAll(articles: List<FavArticle>)

    @Query("DELETE FROM fav_article WHERE id = :articleId")
    fun deleteByArticleId(articleId: String)
}
