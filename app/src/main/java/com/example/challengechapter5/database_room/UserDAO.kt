package com.example.challengechapter5.database_room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface UserDAO {
    @Insert
    fun insertUser(userData: UserData)
    @Query("SELECT * FROM table_user")
    fun getUser(): LiveData<UserData>
    @Query("SELECT * FROM table_user WHERE email = :email AND password = :password")
    fun checkUser(email : String, password : String): LiveData<UserData>
    @Update
    fun updateUser(userData: UserData)
}