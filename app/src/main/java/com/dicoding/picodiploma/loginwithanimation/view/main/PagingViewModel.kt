package com.dicoding.picodiploma.loginwithanimation.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding.picodiploma.loginwithanimation.data.pref.ListStoryItem
import com.dicoding.picodiploma.loginwithanimation.data.pref.StoryRepository

class PagingViewModel(repository: StoryRepository) : ViewModel() {

    val getListStory: LiveData<PagingData<ListStoryItem>> =
        repository.getListStories().cachedIn(viewModelScope)
}