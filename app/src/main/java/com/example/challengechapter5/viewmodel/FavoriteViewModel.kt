@file:Suppress("unused")

package com.example.challengechapter5.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.challengechapter5.database_room.FavoriteMoviesDAO
import com.example.challengechapter5.database_room.FavoriteMoviesData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("OPT_IN_USAGE", "DeferredResultUnused")
@HiltViewModel
class FavoriteViewModel @Inject constructor(@Suppress("MemberVisibilityCanBePrivate") val favoriteMoviesDAO: FavoriteMoviesDAO): ViewModel() {

    private var _favoriteMovies : MutableLiveData<List<FavoriteMoviesData>> = MutableLiveData()
    val favoriteMovies : LiveData<List<FavoriteMoviesData>> get() = _favoriteMovies

    private var _favoriteMovie : MutableLiveData<FavoriteMoviesData> = MutableLiveData()
    val favoriteMovie : LiveData<FavoriteMoviesData> get() = _favoriteMovie

    @Suppress("PropertyName")
    var _isFavorite = MutableLiveData<Boolean>()
    val isFavorite:LiveData<Boolean> get() = _isFavorite

    val favoriteStatusMap: MutableMap<Int, Boolean> = mutableMapOf()

    fun loadFavoriteStatus(movieId: Int) {
        GlobalScope.launch {
            val isFavorite = favoriteMoviesDAO.getIsFavorite(movieId)
            favoriteStatusMap[movieId] = isFavorite
        }

    }


    fun insertFavoriteMovies(favoriteMoviesData: FavoriteMoviesData){
        GlobalScope.async {
            favoriteMoviesDAO.insert(favoriteMoviesData)
        }
    }

    fun deleteFavoriteMovies(movieId: Int, userId: Int) {
        GlobalScope.async {
            favoriteMoviesDAO.deleteFavoriteMoviesByIdAndUser(movieId, userId)
        }
    }


    fun getFavoriteMovies(userId: Int){
        GlobalScope.launch {
            val listFavoriteMovies = favoriteMoviesDAO.getFavoriteMoviesByUser(userId)
            _favoriteMovies.postValue(listFavoriteMovies)
        }
    }


}