package com.example.challengechapter5.model


import com.google.gson.annotations.SerializedName

data class UpcomingMoviesResponse(
    @SerializedName("dates")
    val dates: Dates,
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<ResultUpcoming>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)