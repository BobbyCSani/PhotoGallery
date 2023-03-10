package com.photo.gallery.data.local

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppDatabaseModule {

    @Singleton
    @Provides
    fun provideRoomDB(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java, "photo-gallery"
        ).build()
}