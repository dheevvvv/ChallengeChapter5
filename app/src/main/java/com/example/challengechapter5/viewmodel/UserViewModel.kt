package com.example.challengechapter5.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.challengechapter5.database_room.MovieDatabase
import com.example.challengechapter5.database_room.UserData
import com.example.challengechapter5.datastore_preferences.UserManager
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class UserViewModel(application: Application):AndroidViewModel(application) {
    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _profilePhoto = MutableLiveData<String>()
    val profilePhoto: LiveData<String> = _username

    private val userDAO = MovieDatabase.getInstance(getApplication())?.userDao()!!

    val userManager = UserManager.getInstance(application)
    fun getUsername() {
        viewModelScope.launch {
            val username = userManager.getUsername()
            _username.value = username
        }
    }

    fun getProfilePhoto() {
        viewModelScope.launch {
            val profilePhoto = userManager.getProfilePhoto()
            _profilePhoto.value = profilePhoto
        }
    }

    fun insertUser(userData: UserData){
        GlobalScope.async {
            val userDAO = MovieDatabase.getInstance(getApplication())?.userDao()!!
            userDAO.insertUser(userData)
        }
    }
    fun checkUser(email : String, password : String) : LiveData<UserData> = MovieDatabase.getInstance((getApplication()))!!.userDao().checkUser(email, password)


    fun updateUser(userData: UserData){
        GlobalScope.async {
            val userDAO = MovieDatabase.getInstance(getApplication())?.userDao()
            userDAO?.updateUser(userData)
        }
    }

    fun getUser(): LiveData<UserData> {
        return userDAO.getUser()
    }

//    fun getUserId(): LiveData<Int> {
//        val userIdLiveData = userDAO.getUserIdByUsername(username)
//        return userIdLiveData
//    }





}