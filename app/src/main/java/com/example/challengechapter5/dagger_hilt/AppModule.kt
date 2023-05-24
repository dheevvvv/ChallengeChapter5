package com.example.challengechapter5.dagger_hilt

import android.app.Application
import com.example.challengechapter5.database_room.FavoriteMoviesDAO
import com.example.challengechapter5.database_room.MovieDatabase
import com.example.challengechapter5.database_room.UserDAO
import com.example.challengechapter5.datastore_preferences.UserManager
import com.example.challengechapter5.networking.ApiKeyInterceptor
import com.example.challengechapter5.networking.ApiService
import com.example.challengechapter5.networking.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesMovieDatabase(application: Application): MovieDatabase {
        return MovieDatabase.getInstance(application)!!
    }

    @Singleton
    @Provides
    fun providesUserDao(userDao:MovieDatabase):UserDAO{
        return userDao.userDao()
    }

    @Singleton
    @Provides
    fun provideUserManager(application: Application): UserManager {
        return UserManager.getInstance(application)
    }

    @Singleton
    @Provides
    fun providesFavoriteMoviesDao(favoriteMoviesDao:MovieDatabase):FavoriteMoviesDAO{
        return favoriteMoviesDao.favoriteMoviesDao()
    }

    private const val BASE_URL ="https://api.themoviedb.org/3/"
    private const val API_KEY = "6cb32867a94de7a19988927b1aece140"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(ApiKeyInterceptor(API_KEY))
        .build()

    @Singleton
    @Provides
    fun provideRetrofitClient(): Retrofit =
    Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideApiKeyInterceptor(): ApiKeyInterceptor {
        val apiKey = "6cb32867a94de7a19988927b1aece140"
        return ApiKeyInterceptor(apiKey)
    }
    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)



}