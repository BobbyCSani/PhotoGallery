package com.photo.gallery.uikit

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.photo.gallery.data.model.PhotoUIModel
import com.photo.gallery.utility.isVisible
import com.simple.news.databinding.UikitPhotoCardBinding

class PhotoCard(context: Context, attributeSet: AttributeSet?): ConstraintLayout(context, attributeSet) {

    private val binding = UikitPhotoCardBinding.inflate(LayoutInflater.from(context), this)

    fun setData(data: PhotoUIModel) = with(binding){
        Glide.with(context).load(data.smallUrl).into(ivThumbnail)
        tvDescription.isVisible(data.description.isNotEmpty())
        tvDescription.text = data.description
        tvAuthor.text = data.username
        tvTimestamp.text = data.date
    }
}