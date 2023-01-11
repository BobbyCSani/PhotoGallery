package com.photo.gallery.data.mapper

import com.photo.gallery.data.model.PhotoResponseModel
import com.photo.gallery.data.model.PhotoUIModel
import com.photo.gallery.utility.ddMMMyyyy

fun PhotoResponseModel.toPhotoUIModel() =
    PhotoUIModel(
        id = id,
        date = created_at.ddMMMyyyy(),
        description = description ?: alt_description ?: "",
        thumbnailUrl = urls?.thumb ?: "",
        smallUrl = urls?.small ?: "",
        username = user?.name ?: "",
        isLiked = false
    )