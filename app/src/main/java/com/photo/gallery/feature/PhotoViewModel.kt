package com.photo.gallery.feature

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.photo.gallery.data.model.PhotoUIModel
import com.photo.gallery.data.repository.PhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(private val repository: PhotoRepository): ViewModel() {

    private val _photoLiveData = MutableLiveData<PagingData<PhotoUIModel>>()
    val photoLiveData: LiveData<PagingData<PhotoUIModel>>
        get() = _photoLiveData

    //true for list, grid otherwise
    private val _photoStyleLiveData = MutableLiveData<Boolean>()
    val photoStyleLiveData: LiveData<Boolean>
        get() = _photoStyleLiveData

    private suspend fun setPagingData(request: Flow<PagingData<PhotoUIModel>>) {
        request.cachedIn(viewModelScope).collect { data ->
            _photoLiveData.value = data
        }
    }

    fun setPhotoStyle(isListStyle: Boolean) {
        _photoStyleLiveData.value = isListStyle
    }

    fun getRandomPhoto() = viewModelScope.launch {
        setPagingData(repository.getRandomPhoto())
    }

    fun searchPhoto(query: String) = viewModelScope.launch {
        setPagingData(repository.searchPhoto(query))
    }
}