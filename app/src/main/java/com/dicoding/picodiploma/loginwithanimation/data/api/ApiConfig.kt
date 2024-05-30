package com.dicoding.picodiploma.loginwithanimation.data.api

import com.dicoding.picodiploma.loginwithanimation.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    fun getApiService(token: String?): ApiService {
        val loggingInterceptor =
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
            }
        val authInterceptor = Interceptor { chain ->
            val req = chain.request()
            val requestHeaders = req.newBuilder()
            if (!token.isNullOrEmpty()) {
                requestHeaders.addHeader("Authorization", "Bearer $token")
            }
            chain.proceed(requestHeaders.build())
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }

    companion object {
        const val BASE_URL = "https://story-api.dicoding.dev/v1/"
    }
}
