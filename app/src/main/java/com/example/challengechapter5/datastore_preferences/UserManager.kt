package com.example.challengechapter5.datastore_preferences

import android.annotation.SuppressLint
import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import com.example.challengechapter5.database_room.FavoriteMoviesData
import com.example.challengechapter5.database_room.MovieDatabase
import com.example.challengechapter5.database_room.UserData
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class UserManager(private val context: Context) {
    private val preferenceName = "prefs"
    private val Context.datastore by preferencesDataStore(preferenceName)

    private val USERNAME = stringPreferencesKey("username")
    private val EMAIL = stringPreferencesKey("email")
    private val PASSWORD = stringPreferencesKey("password")
    private val IS_LOGIN_KEY = booleanPreferencesKey("is_login")
    private val PROFILE_PHOTO = stringPreferencesKey("profile_photo")

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile private var instance: UserManager? = null

        fun getInstance(context: Context): UserManager {
            return instance ?: synchronized(this) {
                instance ?: UserManager(context).also { instance = it }
            }
        }
    }

    suspend fun saveData (username:String, email:String, password:String, is_login_key:Boolean, profile_photo:String){
        context.datastore.edit {
            it [USERNAME] = username
            it [EMAIL] = email
            it [PASSWORD] = password
            it [IS_LOGIN_KEY] = is_login_key
            it [PROFILE_PHOTO] = profile_photo
        }
    }

    suspend fun updateUsername(username: String) {
        context.datastore.edit { preferences ->
            preferences[USERNAME] = username
        }
    }

    suspend fun updatePhotoProfile(photoProfile: String) {
        context.datastore.edit { preferences ->
            preferences[PROFILE_PHOTO] = photoProfile
        }
    }


    suspend fun clearData(){
        context.datastore.edit {
            it.clear()
        }
    }

    fun isLoggedIn(): Flow<Boolean> {
        return context.datastore.data
            .map { preferences ->
                preferences[IS_LOGIN_KEY] ?: false
            }
    }

    suspend fun getEmail():String{
        val preferences = context.datastore.data.first()
        return preferences[EMAIL] ?: ""
    }

    suspend fun getUsername(): String {
        val preferences = context.datastore.data.first()
        return preferences[USERNAME] ?: ""
    }

    suspend fun getProfilePhoto(): String {
        val preferences = context.datastore.data.first()
        return preferences[PROFILE_PHOTO] ?: ""
    }

//    suspend fun addFavoriteMovie(movieId: Int) {
//        val userId = getUserId()
//        if (userId.isNotBlank()) {
//            val favoriteMovie = FavoriteMoviesData(userId = userId.toInt(), movieId = movieId, )
//            movieDatabase.movieDao().insertFavoriteMovie(favoriteMovie)
//        }
//    }
}