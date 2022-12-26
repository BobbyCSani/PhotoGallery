package com.photo.gallery.data.repository

import androidx.paging.PagingData
import com.photo.gallery.data.mapper.toPhotoUIModel
import com.photo.gallery.data.model.PhotoUIModel
import com.photo.gallery.data.pagination.BasePagingSource
import com.photo.gallery.data.pagination.PageUtils
import com.photo.gallery.data.pagination.PagingDataModel
import com.photo.gallery.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PhotoRepoImpl @Inject constructor(private val apiService: ApiService): PhotoRepository {

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
}