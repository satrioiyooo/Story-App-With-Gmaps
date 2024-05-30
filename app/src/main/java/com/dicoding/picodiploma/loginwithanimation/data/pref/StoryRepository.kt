package com.dicoding.picodiploma.loginwithanimation.data.pref

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.dicoding.picodiploma.loginwithanimation.data.StoryRemoteMediator
import com.dicoding.picodiploma.loginwithanimation.data.api.ApiService
import com.dicoding.picodiploma.loginwithanimation.data.database.StoryDatabase

class StoryRepository(private val context: Context, private val apiService: ApiService, private val storyDatabase: StoryDatabase) {
    fun getListStories(): LiveData<PagingData<ListStoryItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 10 ,
                enablePlaceholders = false

            ),
            remoteMediator = StoryRemoteMediator(context, storyDatabase),
            pagingSourceFactory = {
                storyDatabase.storyDao().getAllStory()
            }

        ).liveData
    }
}