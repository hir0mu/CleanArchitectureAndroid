package com.hiring.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hiring.data.entity.FavArticleEntity

@Dao
interface ArticleDao {
    @Query("SELECT * FROM fav_article order by created_at desc")
    fun getAll(): List<FavArticleEntity>

    @Query("SELECT * FROM fav_article WHERE id IN (:articleIds)")
    fun getArticlesByIds(articleIds: List<String>): List<FavArticleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(articles: List<FavArticleEntity>)

    @Query("DELETE FROM fav_article WHERE id = :articleId")
    fun deleteByArticleId(articleId: String)
}
