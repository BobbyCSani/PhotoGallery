package com.photo.gallery.data.repository

import androidx.paging.PagingData
import com.photo.gallery.data.model.PhotoUIModel
import kotlinx.coroutines.flow.Flow

interface PhotoRepository {

    suspend fun getRandomPhoto(): Flow<PagingData<PhotoUIModel>>
    suspend fun searchPhoto(query: String): Flow<PagingData<PhotoUIModel>>
}