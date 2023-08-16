package com.example.movie.network

import com.example.movie.utils.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/*
* @Module annotation is used to notify dager-hilt that this is the module class
* of our project which can used the function declaration of needed instance of class.
* */

/*
* @Provides annotation is used to notify that this function will be put where
* you want by @Inject.
* */

@Module
@InstallIn(SingletonComponent::class)
class RetrofitHelper {
    @Provides
    @Singleton
    fun retrofitInstance(): ApiService {
        val apiService: ApiService by lazy {
            Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
        return apiService
    }
}