package com.dicoding.picodiploma.loginwithanimation.view.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dicoding.picodiploma.loginwithanimation.data.UserRepository
import kotlinx.coroutines.Dispatchers

class MapsViewModel(private val repository: UserRepository) : ViewModel() {

    fun getStoriesWithLocation() = liveData(Dispatchers.IO) {
        try {
            val response = repository.getStoriesWithLocation()
            emit(response)
        } catch (e: Exception) {
            emit(null)
        }
    }
}
