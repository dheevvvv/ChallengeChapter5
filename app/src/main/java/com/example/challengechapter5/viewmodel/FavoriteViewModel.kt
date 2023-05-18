package com.example.challengechapter5.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.challengechapter5.database_room.FavoriteMoviesData
import com.example.challengechapter5.database_room.MovieDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application): AndroidViewModel(application) {

    private var _favoriteMovies : MutableLiveData<List<FavoriteMoviesData>> = MutableLiveData()
    val favoriteMovies : LiveData<List<FavoriteMoviesData>> get() = _favoriteMovies

    private val favoriteMoviesDao = MovieDatabase.getInstance(getApplication())?.favoriteMoviesDao()!!


    fun insertFavoriteMovies(favoriteMoviesData: FavoriteMoviesData){
        GlobalScope.async {
            favoriteMoviesDao.insert(favoriteMoviesData)
        }
    }

    fun getFavoriteMovies(userId: Int){
        GlobalScope.launch {
            val listFavoriteMovies = favoriteMoviesDao.getFavoriteMoviesByUser(userId)
            _favoriteMovies.postValue(listFavoriteMovies)
        }
    }


}