package com.dicoding.mystory.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.dicoding.mystory.databinding.ActivityDetailBinding
import com.dicoding.mystory.utils.ViewModelFactory
import com.dicoding.mystory.utils.loadImage
import com.dicoding.mystory.utils.showLoading

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModels { ViewModelFactory.getInstance(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra(ID)

        detailViewModel.getDetailStory(id.toString())

        detailViewModel.isLoading.observe(this){
            binding.progressBar.showLoading(it)
        }

        detailViewModel.detailStory.observe(this){ story ->
            binding.apply {
                story.photoUrl?.let { imgDetail.loadImage(it) }
                tvTitle.text = story.name
                tvDescription.text = story.description
            }
        }

    }
    companion object{
        const val ID = "story-id"
    }
}