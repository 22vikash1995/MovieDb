package com.example.movie.network

import com.example.movie.model.NowPlayingMovieModel
import com.example.movie.model.PopularMovieModel
import com.example.movie.utils.Constant
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {
    @GET("now_playing")
    @Headers("Authorization:${Constant.TOKEN},accept:${Constant.CONTENT_TYPE}")
    suspend fun nowPlayingMovie(@Query("api_key") api_key: String): Response<NowPlayingMovieModel>

    @GET("popular")
    @Headers("Authorization:${Constant.TOKEN},accept:${Constant.CONTENT_TYPE}")
    fun getPopularMovie(@Query("api_key") api_key: String): Call<PopularMovieModel>
}