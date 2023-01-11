package com.photo.gallery.data.repository

import android.provider.ContactsContract.CommonDataKinds.Photo
import androidx.paging.PagingData
import com.photo.gallery.data.model.PhotoUIModel
import com.photo.gallery.utility.ResultState
import kotlinx.coroutines.flow.Flow

interface PhotoRepository {

    suspend fun getRandomPhoto(): Flow<PagingData<PhotoUIModel>>
    suspend fun searchPhoto(query: String): Flow<PagingData<PhotoUIModel>>
    suspend fun favoritePhoto(data: PhotoUIModel)
    suspend fun getFavoritePhoto(photoId: String): Flow<ResultState<PhotoUIModel>>
}