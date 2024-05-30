package com.dicoding.picodiploma.loginwithanimation.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.dicoding.picodiploma.loginwithanimation.data.api.ApiConfig
import com.dicoding.picodiploma.loginwithanimation.data.UserRepository
import com.dicoding.picodiploma.loginwithanimation.data.pref.StoryResponse
import com.dicoding.picodiploma.loginwithanimation.data.pref.ListStoryItem
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val repository: UserRepository) : ViewModel() {

    private val _stories = MutableLiveData<ArrayList<ListStoryItem>>()
    val stories: LiveData<ArrayList<ListStoryItem>> = _stories

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess

    fun getSession() = repository.getSession().asLiveData()

//    fun getStories(token: String) {
//        val call = ApiConfig().getApiService(token).getStories(page, state.config.pageSize)
//        call.enqueue(object : Callback<StoryResponse> {
//            override fun onResponse(call: Call<StoryResponse>, response: Response<StoryResponse>) {
//                if (response.isSuccessful) {
//                    _stories.value = response.body()?.listStory
//                    _isSuccess.value = true
//                } else {
//                    _isSuccess.value = false
//                }
//            }
//
//            override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
//                _isSuccess.value = false
//            }
//        })
//    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}
