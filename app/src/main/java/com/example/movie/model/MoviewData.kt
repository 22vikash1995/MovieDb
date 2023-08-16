package com.example.movie.model

import com.google.gson.annotations.SerializedName
/*
*@SerializedName annotation is used where 'key'(i.e original_title)
* and 'value'(i.e initial_title)is is different .
* e.g @SerializedName will hand-me-down the serialize field with another name rather than
*  its actual name. If API give me the “original_title” field but i have to use
*  “initial_title” so i will put down the “original_title” in like @SerializedName(“original_title”)
*  and then declare that “initial_title”, then i will able to use that “initial_title”
* all over the project and it will work like “original_title”
*  */
data class PopularMovieModel(
    val popularMovieData: List<PopularResults>,
)

data class PopularResults(
    @SerializedName("original_title") var initial_title:String?=null,
    @SerializedName("overview") var overview:String?=null,
    @SerializedName("poster_path") var poster_path:String?=null,
    @SerializedName("release_date") var release_date:String?=null,
    @SerializedName("title") var title:String?=null,
)

data class NowPlayingMovieModel(
    val page:Int,
    val results:List<NowPlayingMovieResults>
)

data class NowPlayingMovieResults(
    @SerializedName("original_title") var original_title:String?=null,
    @SerializedName("overview") var overview:String?=null,
    @SerializedName("popularity") var popularity:String?=null,
    @SerializedName("poster_path") var poster_path:String?=null,
    @SerializedName("release_date") var release_date:String?=null,
    @SerializedName("title") var title:String?=null,
    @SerializedName("rating") var rating:String?=null,
)