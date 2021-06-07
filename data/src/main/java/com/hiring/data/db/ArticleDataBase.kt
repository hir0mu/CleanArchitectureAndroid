package com.hiring.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hiring.data.entity.FavArticle

@Database(entities = [FavArticle::class], version = 1)
abstract class ArticleDataBase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}
