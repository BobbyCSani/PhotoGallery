package com.photo.gallery.data.repository

import android.provider.ContactsContract
import androidx.paging.PagingData
import com.photo.gallery.data.local.AppDatabase
import com.photo.gallery.data.mapper.toPhotoUIModel
import com.photo.gallery.data.model.PhotoUIModel
import com.photo.gallery.data.pagination.BasePagingSource
import com.photo.gallery.data.pagination.PageUtils
import com.photo.gallery.data.pagination.PagingDataModel
import com.photo.gallery.network.ApiService
import com.photo.gallery.utility.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PhotoRepoImpl @Inject constructor(
    private val apiService: ApiService,
    private val database: AppDatabase
    ): PhotoRepository {

    override suspend fun getRandomPhoto(): Flow<PagingData<PhotoUIModel>> =
        PageUtils.createPager {
            BasePagingSource { loadParam ->
                val response = apiService.getPhotos(
                    page = loadParam.key ?: PageUtils.FIRST_PAGE)
                val result = response.body()?.map { it.toPhotoUIModel() } ?: listOf()
                PagingDataModel(
                    responseCode = response.code(),
                    data = response.body()?.map { it.toPhotoUIModel() } ?: listOf(),
                    hasNext = result.size >= 20,
                    isSuccess = response.isSuccessful,
                    errorBody = response.errorBody()
                )
            }
        }.flow.flowOn(Dispatchers.IO)

    override suspend fun searchPhoto(query: String): Flow<PagingData<PhotoUIModel>> =
        PageUtils.createPager {
            BasePagingSource { loadParam ->
                val response = apiService.searchPhotos(query = query,
                    page = loadParam.key ?: PageUtils.FIRST_PAGE)
                val result = response.body()?.results?.map { it.toPhotoUIModel() } ?: listOf()
                PagingDataModel(
                    responseCode = response.code(),
                    data = result,
                    hasNext = result.size >= 20,
                    isSuccess = response.isSuccessful,
                    errorBody = response.errorBody()
                )
            }
        }.flow.flowOn(Dispatchers.IO)

    override suspend fun favoritePhoto(data: PhotoUIModel) {
        withContext(Dispatchers.IO){
            database.favoriteDao().favoritePhoto(listOf(data))
        }
    }

    override suspend fun getFavoritePhoto(photoId: String): Flow<ResultState<PhotoUIModel>> {
        return flow {
            try {
                val result = database.favoriteDao().getPhotoById(photoId)
                emit(ResultState.Success(result[0]))
            } catch (e: Exception) {
                emit(ResultState.Failed(e))
            }
        }.flowOn(Dispatchers.IO)
    }
}