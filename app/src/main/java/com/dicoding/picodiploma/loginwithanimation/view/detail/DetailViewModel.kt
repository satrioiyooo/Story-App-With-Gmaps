package com.dicoding.picodiploma.loginwithanimation.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.loginwithanimation.data.pref.ListStoryItem

class DetailViewModel : ViewModel() {
    private val _story = MutableLiveData<ListStoryItem>()
    val story: LiveData<ListStoryItem> = _story

    fun setStory(story: ListStoryItem) {
        _story.value = story
    }
}