package com.dicoding.picodiploma.loginwithanimation.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.loginwithanimation.data.pref.ListStoryItem
import com.dicoding.picodiploma.loginwithanimation.databinding.StoryItemBinding

class AdapterStory : PagingDataAdapter<ListStoryItem, AdapterStory.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            StoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val story = getItem(position)
        story?.let { holder.bind(it, onItemClickCallback) }
    }

    class ViewHolder(private val binding: StoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(story: ListStoryItem, clickCallback: OnItemClickCallback) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(story.photoUrl)
                    .into(imageView)

                Judul.text = story.name

                itemView.setOnClickListener {
                    val sharedViews = arrayOf(
                        Pair(imageView, "profile"),
                        Pair(Judul, "name")
                    )
                    clickCallback.onItemClicked(story, sharedViews)
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ListStoryItem, sharedViews: Array<Pair<View, String>>)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
