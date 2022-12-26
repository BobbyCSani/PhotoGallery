package com.photo.gallery.data.model

import androidx.recyclerview.widget.DiffUtil

data class PhotoResponseModel(
    val id: String,
    val created_at: String?,
    val description: String?,
    val alt_description: String?,
    val urls: ImageUrl?,
    val user: UserModel?
)

data class SearchPhotoResponseModel(
    val results: List<PhotoResponseModel>
)

data class PhotoUIModel(
    val id: String,
    val date: String,
    val description: String,
    val thumbnailUrl: String,
    var smallUrl: String,
    val username: String
)

data class ImageUrl(
    val small: String,
    val thumb: String
)

data class UserModel(
    val name: String
)

class DiffUtilsPhotoUIModel : DiffUtil.ItemCallback<PhotoUIModel>() {
    override fun areItemsTheSame(
        oldItem: PhotoUIModel,
        newItem: PhotoUIModel
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: PhotoUIModel,
        newItem: PhotoUIModel
    ): Boolean {
        return oldItem == newItem
    }
}
