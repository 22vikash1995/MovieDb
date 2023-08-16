package com.example.movie.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movie.R
import com.example.movie.databinding.RawMoviesItemLayoutBinding
import com.example.movie.model.NowPlayingMovieResults
import com.example.movie.utils.Constant

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(val binding: RawMoviesItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(result: NowPlayingMovieResults) {
            binding.apply {
                //setting the movie banner
                Glide.with(root.context)
                    .load(Constant.IMAGE_BASE_URL+result.poster_path)
                    .placeholder(R.drawable.ic_movie)
                    .into(binding.imageOfMovies)

                //setting textual data
                binding.titleOfMovie.text = result.original_title
                binding.descriptionOfMovie.text = result.overview
            }
        }
    }

    //let’s create an ItemCallback of diffUtils.the old list and new list,
    private val differCallBack = object : DiffUtil.ItemCallback<NowPlayingMovieResults>() {
        override fun areItemsTheSame(
            oldItem: NowPlayingMovieResults,
            newItem: NowPlayingMovieResults
        ): Boolean {
            return oldItem.original_title == newItem.original_title
        }

        override fun areContentsTheSame(
            oldItem: NowPlayingMovieResults,
            newItem: NowPlayingMovieResults
        ): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, differCallBack)

    //Create one result variable which can contain a list of result class.
    // For that we have to use the getter-setter method in Kotlin.
    var result: List<NowPlayingMovieResults>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            RawMoviesItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return result.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(result[position])
    }
}