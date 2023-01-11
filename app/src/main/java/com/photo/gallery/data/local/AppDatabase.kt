package com.photo.gallery.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.photo.gallery.data.model.PhotoUIModel

@Database(entities = [PhotoUIModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}