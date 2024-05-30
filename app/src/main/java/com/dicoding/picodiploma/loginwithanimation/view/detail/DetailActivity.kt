package com.dicoding.picodiploma.loginwithanimation.view.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.loginwithanimation.data.pref.ListStoryItem
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityDetailBinding

@Suppress("DEPRECATION")
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by lazy {
        ViewModelProvider(this).get(DetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val story = intent.getParcelableExtra<ListStoryItem>(EXTRA_STORY)
        if (story != null) {
            viewModel.setStory(story)
        }

        viewModel.story.observe(this) {
            bindStoryData(it)
        }
    }

    private fun bindStoryData(story: ListStoryItem) {
        binding.apply {
            Glide.with(this@DetailActivity)
                .load(story.photoUrl)
                .into(imageView2)

            textView.text = story.name
            textView2.text = story.description
        }
    }

    companion object {
        const val EXTRA_STORY = "extra_story"
    }
}