package com.dicoding.picodiploma.loginwithanimation.view.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.loginwithanimation.data.pref.ListStoryItem
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityMainBinding
import com.dicoding.picodiploma.loginwithanimation.view.StoryModelFactory
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory
import com.dicoding.picodiploma.loginwithanimation.view.camera.MainCameraActivity
import com.dicoding.picodiploma.loginwithanimation.view.detail.DetailActivity
import com.dicoding.picodiploma.loginwithanimation.view.maps.MapsActivity
import com.dicoding.picodiploma.loginwithanimation.view.welcome.WelcomeActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

//    private lateinit var factory: StoryModelFactory
    private val storyViewModel by viewModels<PagingViewModel>{
        StoryModelFactory.getInstance(this)
    }

    private val viewModel by viewModels <MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var adapterStory: AdapterStory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        factory = StoryModelFactory.getInstance(binding.root.context)

        getStories()
        setupView()
        upload()
    }

    private fun upload() {
        val share = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        share.setOnClickListener{
            val intent = Intent(this, MainCameraActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getStories() {
        viewModel.getSession().observe(this) {
//            storyViewModel.getListStory.observe(this) { story ->
//                adapterStory.submitData(lifecycle, story)
//            }
            if (!it.isLogin) {
                startActivity(Intent(this@MainActivity, WelcomeActivity::class.java))
                finish()
            } else {
            recyclerView(it.token)
            }
        }
    }

    private fun recyclerView(token: String) {
        setLoading(true)
//        viewModel.getStories(token)
        storyViewModel.getListStory.observe(this){ story ->
            setLoading(false)
            adapterStory = AdapterStory()
            adapterStory.submitData(lifecycle, story)
            binding.apply {
                rvStory.adapter = adapterStory
                rvStory.layoutManager = LinearLayoutManager(this@MainActivity)
                rvStory.setHasFixedSize(true)

                adapterStory.setOnItemClickCallback(object : AdapterStory.OnItemClickCallback {
                    override fun onItemClicked(
                        data: ListStoryItem,
                        sharedViews: Array<Pair<View, String>>
                    ) {
                        val sharedViewsCompat = sharedViews.map { androidx.core.util.Pair(it.first, it.second) }.toTypedArray()
                        val intent = Intent(this@MainActivity, DetailActivity::class.java).apply {
                            putExtra(DetailActivity.EXTRA_STORY, data)
                        }
                        val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                            this@MainActivity,
                            *sharedViewsCompat)
                        startActivity(intent, optionsCompat.toBundle())
                    }
                })
            }
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }
    private fun setLoading(isLoading: Boolean) = binding.progressBar.isVisible == isLoading
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu1 -> {
                val intent = Intent(this@MainActivity, MapsActivity::class.java) // Assuming MapsActivity exists
                startActivity(intent)
                true
            }
            R.id.floatingActionButton2 -> {
                viewModel.logout()
                startActivity(Intent(this@MainActivity, WelcomeActivity::class.java))
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
