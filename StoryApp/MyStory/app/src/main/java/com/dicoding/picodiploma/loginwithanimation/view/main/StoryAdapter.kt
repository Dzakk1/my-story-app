package com.dicoding.picodiploma.loginwithanimation.view.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.loginwithanimation.data.response.ListStoryItem
import com.dicoding.picodiploma.loginwithanimation.databinding.ItemStoryBinding
import com.dicoding.picodiploma.loginwithanimation.view.story.DetailStoryActivity

class StoryAdapter : PagingDataAdapter<ListStoryItem, StoryAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val story = getItem(position)
        if (story != null) {
            holder.bind(story)
        }
    }

    class MyViewHolder (val binding: ItemStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(story : ListStoryItem) {
            binding.tvAuthStory.text = story.name
            binding.tvDescStory.text = story.description
            Glide.with(itemView)
                .load(story.photoUrl)
                .into(binding.imgStory)

            val clickStory = binding.root.context
            itemView.setOnClickListener {
                val intent = Intent(clickStory, DetailStoryActivity::class.java)

                intent.putExtra(DetailStoryActivity.EXTRA_ID, story.id)
                intent.putExtra(DetailStoryActivity.EXTRA_AUTHOR, story.name)
                intent.putExtra(DetailStoryActivity.EXTRA_DESCRIPTION, story.description)
                intent.putExtra(DetailStoryActivity.EXTRA_IMAGE, story.photoUrl)

                clickStory.startActivity(intent)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>(){
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ListStoryItem,
                newItem: ListStoryItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}


