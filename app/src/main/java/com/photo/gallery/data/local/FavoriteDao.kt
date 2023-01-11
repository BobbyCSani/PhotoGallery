package com.photo.gallery.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.photo.gallery.data.model.PhotoUIModel

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM photouimodel where id=(:id)")
    fun getPhotoById(id: String): List<PhotoUIModel>

    @Insert(onConflict = REPLACE)
    fun favoritePhoto(data: List<PhotoUIModel>)


}