package com.example.challengechapter5.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.challengechapter5.database_room.MovieDatabase
import com.example.challengechapter5.database_room.UserData
import com.example.challengechapter5.datastore_preferences.UserManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class UserViewModel(application: Application):AndroidViewModel(application) {
    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    val userManager = UserManager.getInstance(application)
    fun getUsername() {
        viewModelScope.launch {
            val username = userManager.getUsername()
            _username.value = username
        }
    }

    fun insertUser(userData: UserData){
        viewModelScope.launch() {
            val userDAO = MovieDatabase.getInstance(getApplication())?.userDao()!!
            userDAO.insertUser(userData)
        }
    }
    fun checkUser(email : String, password : String) : LiveData<UserData> = MovieDatabase.getInstance((getApplication()))!!.userDao().checkUser(email, password)

    fun updateUser(userData: UserData){
        viewModelScope.launch {
            val userDAO = MovieDatabase.getInstance(getApplication())?.userDao()
            userDAO?.updateUser(userData)
        }
    }

}