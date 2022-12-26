package com.photo.gallery.feature

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.photo.gallery.utility.inputListener
import com.simple.news.R
import com.simple.news.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<PhotoViewModel>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupFragment()
        setListener()
        viewModel.getRandomPhoto()
    }

    private fun setupFragment(){
        val fragment = PhotoFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment, PhotoFragment.TAG)
            .commit()
    }

    private fun setListener() = with(binding){
        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
        btnList.setOnClickListener {
            viewModel.setPhotoStyle(isListStyle = true)
        }
        btnGrid.setOnClickListener {
            viewModel.setPhotoStyle(isListStyle = false)
        }
        searchInput.inputListener(lifecycleScope){ query ->
            ListPhotoActivity.start(this@MainActivity, query)
        }
    }
}