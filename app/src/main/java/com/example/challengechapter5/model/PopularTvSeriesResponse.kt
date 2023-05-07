package com.example.challengechapter5.model


import com.google.gson.annotations.SerializedName

data class PopularTvSeriesResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<ResultPopularTvSeries>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)