package com.photo.gallery.feature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.photo.gallery.data.model.PhotoUIModel
import com.photo.gallery.utility.FooterLoadingAdapter
import com.simple.news.databinding.FragmentPhotoBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PhotoFragment: Fragment() {

    companion object {
        const val TAG = "PhotoFragment"
        const val QUERY_KEY = "Query"

        fun newInstance(query: String = "") =
            PhotoFragment().apply {
                arguments = bundleOf(QUERY_KEY to query)
            }
    }

    private val query by lazy { arguments?.getString(QUERY_KEY) ?: "" }
    private val viewModel by activityViewModels<PhotoViewModel>()
    private var binding: FragmentPhotoBinding? = null
    private lateinit var photoAdapter: PhotoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentPhotoBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupRecipeObserver()
        getData()
    }

    private fun getData(){
        if (query.isEmpty()) viewModel.getRandomPhoto()
        else viewModel.searchPhoto(query)
    }

    private fun setupAdapter() {
        activity?.let { act ->
            photoAdapter = PhotoAdapter(::likeAction, ::checkLike).apply {
                withLoadStateHeaderAndFooter(FooterLoadingAdapter(), FooterLoadingAdapter())
            }

            binding?.rvPhoto?.apply {
                layoutManager = LinearLayoutManager(act)
                adapter = photoAdapter
            }
        }
    }

    private fun likeAction(data: PhotoUIModel){
        viewModel.setBookmark(data)
    }

    private fun checkLike(photoId: String, position: Int){
        viewModel.getPhotoFromLocal(photoId, position)
    }

    private fun setupRecipeObserver() {
        viewModel.photoLiveData.observe(viewLifecycleOwner){ data ->
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED){
                    photoAdapter.submitData(data)
                }
            }
        }

        viewModel.photoStyleLiveData.observe(viewLifecycleOwner) { isListStyle ->
            activity?.let { act ->
                photoAdapter.setPhotoStyle(isListStyle)
                binding?.rvPhoto?.apply {
                    layoutManager = if (isListStyle)
                        LinearLayoutManager(act)
                    else
                        GridLayoutManager(act, 3, GridLayoutManager.VERTICAL, false)
                    adapter = photoAdapter
                }
            }
        }

        viewModel.favoriteLiveData.observe(viewLifecycleOwner){ data ->
            binding?.rvPhoto?.post {
                photoAdapter.setLike(data)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}