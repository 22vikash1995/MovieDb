package com.example.movie.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movie.R
import com.example.movie.adapter.MovieAdapter
import com.example.movie.databinding.ActivityMainBinding
import com.example.movie.viewmodel.PopularMovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var moviesAdapter: MovieAdapter
    private lateinit var viewModel: PopularMovieViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        setUpViewModel()
        setUpRecyclerView()
        setUpUiData()
    }

    private fun setUpUiData() {
        //make api call
        viewModel.getAllData()
       /* viewModel.getMovieData()
        viewModel.getPopularMovieData()
        //getting now playing movie response
        viewModel.apiResponse.observe(this, Observer {
            if (it != null) {
                moviesAdapter.result = it.results
                activityMainBinding.validationTextForSearch.visibility = View.GONE
            } else {
                Toast.makeText(this, "There is some error!", Toast.LENGTH_SHORT).show()
            }
        })
        //getting popular movie response
        viewModel.popularMovie.observe(this, Observer {
            if (it != null) {
                Log.d("popularMovie", it.toString())
                activityMainBinding.validationTextForSearch.visibility = View.GONE
            } else {
                Toast.makeText(this, "There is some error!", Toast.LENGTH_SHORT).show()
            }
        })*/
        //control the progress bar
        viewModel.isProgressShowing.observe(this, Observer {
            if (it) {
                activityMainBinding.mainProgressBar.visibility = View.VISIBLE
                activityMainBinding.validationTextForSearch.visibility = View.GONE
            } else {
                activityMainBinding.mainProgressBar.visibility = View.GONE
                activityMainBinding.validationTextForSearch.visibility = View.VISIBLE
            }
        })

        //check error state
        viewModel.errorMessage.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(this)[PopularMovieViewModel::class.java]
    }

    private fun setUpRecyclerView() = activityMainBinding.movieRecyclerView.apply {
        moviesAdapter = MovieAdapter()
        adapter = moviesAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)

    }
}
