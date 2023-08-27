package com.example.movie.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie.model.NowPlayingMovieModel
import com.example.movie.model.PopularMovieModel
import com.example.movie.network.ApiService
import com.example.movie.utils.Constant
import com.google.android.material.color.utilities.MaterialDynamicColors.onError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import okhttp3.internal.notify
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PopularMovieViewModel @Inject constructor(val apiService: ApiService) : ViewModel() {
    val apiResponse = MutableLiveData<NowPlayingMovieModel>()
    val popularMovie = MutableLiveData<PopularMovieModel>()
    val errorMessage = MutableLiveData<String>()
    val isProgressShowing = MutableLiveData<Boolean>()

    var job: Job? = null

    //handling exceptions
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        onError("Exception Handled : ${throwable.localizedMessage}")
    }

    private fun onError(message: String) {
        errorMessage.value = message
        isProgressShowing.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

    //getting api response
    fun getMovieData(){
        isProgressShowing.value=true
        job=viewModelScope.launch {
            val response=apiService.nowPlayingMovie(Constant.API_KEY)
            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    apiResponse.postValue(response.body())
                    isProgressShowing.value=false
                }else{
                    onError("Error : ${response.message()}")
                }
            }
        }
    }
    fun getPopularMovieData(){
        isProgressShowing.value=true
        job=viewModelScope.launch {
            val response=apiService.getPopularMovie(Constant.API_KEY)
            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    popularMovie.postValue(response.body())
                    isProgressShowing.value=false
                }else{
                    onError("Error : ${response.message()}")
                }
            }
        }
    }
     fun getAllData(){
         try {
             viewModelScope.launch {
                 val call1 = async { apiService.nowPlayingMovie(Constant.API_KEY)}.await()
                 val call2 = async { apiService.getPopularMovie(Constant.API_KEY)}.await()
                 if (call1.isSuccessful&&call2.isSuccessful){
                     Log.d("call1_data:-",call1.body().toString())
                     Log.d("call2_data:-",call2.body().toString())
                 }
             }
         }catch (e:Exception){
            e.printStackTrace()
         }
    }
     suspend fun postData(nowPlayingData: Response<NowPlayingMovieModel>?, popularMovieData: Response<PopularMovieModel>?) {
        withContext(Dispatchers.Main){
            if (popularMovieData!!.isSuccessful){
                popularMovie.postValue(popularMovieData.body())
                isProgressShowing.value=false
            }else{
                onError("Error : ${popularMovieData.message()}")
            }
            if (nowPlayingData!!.isSuccessful){
                apiResponse.postValue(nowPlayingData.body())
                isProgressShowing.value=false
            }else{
                onError("Error : ${nowPlayingData.message()}")
            }
        }

    }
}