package com.photo.gallery.feature

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.simple.news.R
import com.simple.news.databinding.ActivityListPhotoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListPhotoActivity: AppCompatActivity() {

    companion object{
        fun start(context: Context, query: String){
            context.startActivity(Intent(context, ListPhotoActivity::class.java).apply {
                putExtra(PhotoFragment.QUERY_KEY, query)
            })
        }
    }
    private val viewModel by viewModels<PhotoViewModel>()
    private lateinit var binding: ActivityListPhotoBinding
    private val query by lazy { intent?.getStringExtra(PhotoFragment.QUERY_KEY) ?: "" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListPhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupFragment()
        setupListener()
    }

    private fun setupFragment(){
        val fragment = PhotoFragment.newInstance(query = query)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment, PhotoFragment.TAG)
            .commit()
    }

    private fun setupListener() = with(binding){
        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
        btnList.setOnClickListener {
            viewModel.setPhotoStyle(isListStyle = true)
        }
        btnGrid.setOnClickListener {
            viewModel.setPhotoStyle(isListStyle = false)
        }
    }
}