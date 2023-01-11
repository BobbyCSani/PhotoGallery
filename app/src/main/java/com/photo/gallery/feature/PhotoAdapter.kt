package com.photo.gallery.feature

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.photo.gallery.data.model.DiffUtilsPhotoUIModel
import com.photo.gallery.data.model.PhotoUIModel
import com.simple.news.databinding.AdapterPhotoGridBinding
import com.simple.news.databinding.AdapterPhotoListBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class PhotoAdapter(
    private val likeAction: (PhotoUIModel) -> Unit,
    private val checkLike: (String, Int) -> Unit): PagingDataAdapter<PhotoUIModel, PhotoAdapter.CustomViewHolder>(DiffUtilsPhotoUIModel()) {

    private var isListStyle = true

    open class CustomViewHolder(view: View): RecyclerView.ViewHolder(view){
        open fun bind(data: PhotoUIModel) {}
    }

    inner class ListViewHolder(private val binding: AdapterPhotoListBinding): CustomViewHolder(binding.root){

        override fun bind(data: PhotoUIModel) = with(binding){
            photoCard.setData(data, likeAction)
            checkLike(data.id, bindingAdapterPosition)
        }
    }

    inner class GridViewHolder(private val binding: AdapterPhotoGridBinding): CustomViewHolder(binding.root){

        override fun bind(data: PhotoUIModel): Unit = with(binding){
            Glide.with(root.context).load(data.thumbnailUrl).into(ivGrid)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        return if (isListStyle)
            ListViewHolder(
                AdapterPhotoListBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ),
                    parent,
                    false
                )
            )
        else
            GridViewHolder(
                AdapterPhotoGridBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ),
                    parent,
                    false
                )
            )

    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        getItem(position)?.let { data ->
            holder.bind(data)
        }
    }

    fun setPhotoStyle(isListStyle: Boolean){
        this.isListStyle = isListStyle
    }

    fun setLike(data: PhotoUIModel){
        data.position?.let { position ->
            getItem(position)?.let { photo ->
                photo.isLiked = data.isLiked
                notifyItemChanged(position)
            }
        }
    }

}