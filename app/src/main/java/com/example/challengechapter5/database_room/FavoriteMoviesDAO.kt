package com.example.challengechapter5.database_room

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface FavoriteMoviesDAO {
    @Insert
    fun insert(favoriteMoviesData: FavoriteMoviesData)

    @Query("SELECT * FROM favorite_movies_table WHERE userId = :userId")
    fun getFavoriteMoviesByUser(userId: Int): List<FavoriteMoviesData>


    @Query("DELETE FROM favorite_movies_table WHERE movieId = :movieId AND userId = :userId")
    fun deleteFavoriteMoviesByIdAndUser(movieId: Int, userId: Int)

    @Delete
    fun delete(favoriteMoviesData: FavoriteMoviesData)

    @Query("SELECT isFavorite FROM favorite_movies_table WHERE movieId = :movieId")
    fun getIsFavorite(movieId: Int) : Boolean



}