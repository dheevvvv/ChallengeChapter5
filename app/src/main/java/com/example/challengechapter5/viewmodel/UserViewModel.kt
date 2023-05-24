package com.example.challengechapter5.viewmodel


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challengechapter5.database_room.UserDAO
import com.example.challengechapter5.database_room.UserData
import com.example.challengechapter5.datastore_preferences.UserManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("unused", "OPT_IN_USAGE", "DeferredResultUnused", "MemberVisibilityCanBePrivate")
@HiltViewModel
class UserViewModel @Inject constructor(val userDAO: UserDAO, val userManager: UserManager):ViewModel() {
    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _profilePhoto = MutableLiveData<String>()
    val profilePhoto: LiveData<String> = _profilePhoto

    private val _userId = MutableLiveData<Int>()
    val userId: LiveData<Int> = _userId

    private val _profilePhotoBitmap = MutableLiveData<Bitmap>()
    val profilePhotoBitmap: LiveData<Bitmap> = _profilePhotoBitmap




    fun getUsername() {
        viewModelScope.launch {
            val username = userManager.getUsername()
            _username.postValue(username)
        }
    }

    fun getEmail(){
        viewModelScope.launch {
            val email = userManager.getEmail()
            _email.postValue(email)
        }
    }

    fun getUserId(email: String){
        GlobalScope.launch {
            val userId = userDAO.getUserIdByEmail(email)
            _userId.postValue(userId)
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
            userDAO.insertUser(userData)
        }
    }
    fun checkUser(email : String, password : String) : LiveData<UserData> = userDAO.checkUser(email, password)


    fun updateUser(userData: UserData){
        GlobalScope.async {
            userDAO.updateUser(userData)
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