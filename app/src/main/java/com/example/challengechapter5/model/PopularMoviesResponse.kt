package com.example.challengechapter5.model


import com.google.gson.annotations.SerializedName

data class PopularMoviesResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<ResultPopular>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)