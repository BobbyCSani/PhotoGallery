package com.photo.gallery.data.model

import androidx.recyclerview.widget.DiffUtil
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

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

@Entity
data class PhotoUIModel(
    @PrimaryKey val id: String,
    @ColumnInfo val date: String,
    @ColumnInfo val description: String,
    @ColumnInfo val thumbnailUrl: String,
    @ColumnInfo var smallUrl: String,
    @ColumnInfo val username: String,
    @ColumnInfo var isLiked: Boolean,
    var position: Int? = null
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
