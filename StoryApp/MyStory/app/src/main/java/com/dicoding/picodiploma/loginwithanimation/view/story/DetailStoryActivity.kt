package com.dicoding.picodiploma.loginwithanimation.view.story

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.loginwithanimation.data.response.DetailStoryResponse
import com.dicoding.picodiploma.loginwithanimation.data.response.StoryResponse
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityDetailStoryBinding
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory
import com.dicoding.picodiploma.loginwithanimation.view.main.MainViewModel

class DetailStoryActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private val detailViewModel by viewModels<DetailStoryViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityDetailStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        viewModel.getSession().observe(this) { user ->
            Log.d("token", "tokenUser: ${user.token}")
            val token = user.token

            val id = intent.getStringExtra(EXTRA_ID)
            detailViewModel.detailStory(token, id.toString())

            val bundle = Bundle()
            bundle.putString(EXTRA_ID, id)

            detailViewModel.story.observe(this) { detailStory ->
                setDetailStory(detailStory)
            }

            detailViewModel.isLoading.observe(this) {
                showLoading(it)
            }
        }
    }

    private fun setDetailStory(story: DetailStoryResponse) {
        val author = intent.getStringExtra(EXTRA_AUTHOR)
        val description = intent.getStringExtra(EXTRA_DESCRIPTION)
        val photoUrl =intent.getStringExtra(EXTRA_IMAGE)

        binding.tvAuthor.text = author
        binding.tvDescStory.text = description

        if (photoUrl != null) {
            Glide.with(this)
                .load(photoUrl)
                .into(binding.imgStory)
            Log.d("DetailStoryActivity", "Berhasil ambil gambar")
        } else {
            Log.e("DetailStoryActivity", "photo null")
        }

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_AUTHOR = "extra_author"
        const val EXTRA_DESCRIPTION = "extra_description"
        const val EXTRA_IMAGE = "extra_image"
    }
}

