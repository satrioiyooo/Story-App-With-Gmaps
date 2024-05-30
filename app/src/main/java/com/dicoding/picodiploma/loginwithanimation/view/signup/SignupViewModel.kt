package com.dicoding.picodiploma.loginwithanimation.view.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.dicoding.picodiploma.loginwithanimation.data.RegisterResponse
import com.dicoding.picodiploma.loginwithanimation.data.UserRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupViewModel (private val repository: UserRepository): ViewModel (){
    private val _registerResult = MutableLiveData<RegisterResponse>()
    val registerResult : LiveData<RegisterResponse> = _registerResult

private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> get() = _isSuccess


    fun register(name: String, email: String, password: String) {
        _isSuccess.value = false
        viewModelScope.launch {
            val retrofit = repository.register(name, email, password)
            retrofit.enqueue(object : Callback<RegisterResponse> {
                override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                    if (response.isSuccessful && response.body()?.error == false) {
                        _registerResult.value = response.body()
                        _isSuccess.value = true
                    } else {
                        _isSuccess.value = false
                    }
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    _isSuccess.value = false
                }
            })
        }
    }
}