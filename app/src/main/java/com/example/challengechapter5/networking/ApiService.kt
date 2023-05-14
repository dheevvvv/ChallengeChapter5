package com.example.challengechapter5.networking

import com.example.challengechapter5.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    fun getPopularMovies(): Call<PopularMoviesResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(): Call<PopularMoviesResponse>

    @GET("movie/upcoming")
    fun getUpcomingMovies():Call<PopularMoviesResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetail(
        @Path("movie_id") movie_id:Int
    ):Call<MoviesDetailResponse>

    @GET("tv/popular")
    fun getTvPopular():Call<PopularMoviesResponse>

    @GET("tv/top_rated")
    fun getTvOnTheAir():Call<PopularMoviesResponse>

    @GET("tv/{series_id}")
    fun getTvSeriesDetail(
        @Path("series_id") series_id :Int
    ):Call<TvSeriesDetailResponse>


}