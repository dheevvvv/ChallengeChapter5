package com.example.challengechapter5.viewmodel

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
    val profilePhoto: LiveData<String> = _profilePhoto

    private val _profilePhotoBitmap = MutableLiveData<Bitmap>()
    val profilePhotoBitmap: LiveData<Bitmap> = _profilePhotoBitmap

    private val userDAO = MovieDatabase.getInstance(getApplication())?.userDao()!!

    val userManager = UserManager.getInstance(application)


    fun getUsername() {
        viewModelScope.launch {
            val username = userManager.getUsername()
            _username.postValue(username)
        }
    }

    fun getProfilePhoto() {
        viewModelScope.launch {
            val profilePhoto = userManager.getProfilePhoto()
            _profilePhoto.postValue(profilePhoto)
        }
    }

    fun updateUsername(username:String){
        viewModelScope.launch {
            val usernameUpdate = userManager.updateUsername(username)
            _username.postValue(usernameUpdate.toString())
        }
    }
    fun updatePhotoProfile(photoProfile:String){
        viewModelScope.launch {
            val photoUpdate = userManager.updatePhotoProfile(photoProfile)
            _profilePhoto.postValue(photoUpdate.toString())
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

    private fun base64ToBitmap(encodedImage: String): Bitmap? {
        val decodedByteArray = android.util.Base64.decode(encodedImage, android.util.Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.size)
    }

    fun getBitmapFromProfilePhoto(photoProfile: String): Bitmap? {
        return base64ToBitmap(photoProfile)
    }

    fun setProfilePhotoBitmap(bitmap: Bitmap) {
        _profilePhotoBitmap.postValue(bitmap)
    }



//    fun getUserId(): LiveData<Int> {
//        val userIdLiveData = userDAO.getUserIdByUsername(username)
//        return userIdLiveData
//    }





}