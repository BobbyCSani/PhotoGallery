package com.photo.gallery.network

import com.photo.gallery.data.model.PhotoResponseModel
import com.photo.gallery.data.model.SearchPhotoResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("photos")
    suspend fun getPhotos(
        @Query("page") page: Int = 0,
        @Query("per_page") size: Int = 20,
    ): Response<List<PhotoResponseModel>>

    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("page") page: Int = 0,
        @Query("per_page") size: Int = 20,
        @Query("query") query: String
    ): Response<SearchPhotoResponseModel>
}