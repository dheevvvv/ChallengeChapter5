package com.example.challengechapter5.database_room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserData::class, FavoriteMoviesData::class], version = 4)
abstract class MovieDatabase:RoomDatabase() {

    abstract fun userDao() : UserDAO
    abstract fun favoriteMoviesDao() : FavoriteMoviesDAO

    companion object {

        private var INSTANCE: MovieDatabase? = null

        fun getInstance(context: Context): MovieDatabase? {
            if (INSTANCE == null) {
                synchronized(MovieDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext, MovieDatabase::class.java, "Movie.db"
                    ).fallbackToDestructiveMigration().build()
                }
            }
            return INSTANCE
        }
    }
}