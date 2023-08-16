package com.example.movie.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie.model.NowPlayingMovieModel
import com.example.movie.network.ApiService
import com.example.movie.utils.Constant
import com.google.android.material.color.utilities.MaterialDynamicColors.onError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class PopularMovieViewModel @Inject constructor(val apiService: ApiService) : ViewModel() {
    val apiResponse = MutableLiveData<NowPlayingMovieModel>()
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

    //getting pi response
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
}