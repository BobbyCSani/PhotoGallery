package com.photo.gallery.data.pagination

import okhttp3.ResponseBody

data class PagingDataModel<T>(
    val responseCode: Int,
    val data: List<T>,
    val hasNext: Boolean,
    val isSuccess: Boolean = true,
    val errorBody: ResponseBody? = null
)