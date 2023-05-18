package com.example.challengechapter5.database_room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movies_table", foreignKeys = [ForeignKey(
    entity = UserData::class,
    parentColumns = ["id"],
    childColumns = ["userId"],
    onDelete = ForeignKey.CASCADE
)])
data class FavoriteMoviesData(
    @PrimaryKey
    val movieId:Int,
    @ColumnInfo(name = "userId")
    val userId: Int,
    @ColumnInfo(name = "title")
    val title:String,
    @ColumnInfo(name = "posterpath")
    val posterPath:String,
    @ColumnInfo(name = "voteaverage")
    val voteAverage:Double




)
