package com.hir0mu.cleanarchitecture.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hir0mu.cleanarchitecture.data.entity.FavArticleEntity

@Database(entities = [FavArticleEntity::class], version = 1, exportSchema = false)
abstract class ArticleDataBase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}
