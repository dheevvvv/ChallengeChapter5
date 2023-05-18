package com.example.challengechapter5.database_room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface FavoriteMoviesDAO {
    @Insert
    fun insert(favoriteMoviesData: FavoriteMoviesData)

    @Query("SELECT * FROM favorite_movies_table WHERE userId = :userId")
    fun getFavoriteMoviesByUser(userId: Int): List<FavoriteMoviesData>


}