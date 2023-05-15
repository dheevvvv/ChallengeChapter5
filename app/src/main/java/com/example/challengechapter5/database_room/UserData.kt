package com.example.challengechapter5.database_room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "table_user")
class UserData (
    @PrimaryKey(autoGenerate = true)
    var id : Int,
    @ColumnInfo(name = "username")
    var username : String,
    @ColumnInfo(name = "email")
    var email : String,
    @ColumnInfo(name = "password")
    var password: String,
    @ColumnInfo(name = "profilephoto")
    var profilePhoto: String
): Serializable