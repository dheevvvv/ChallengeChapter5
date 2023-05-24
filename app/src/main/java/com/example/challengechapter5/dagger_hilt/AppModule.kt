package com.example.challengechapter5.dagger_hilt

import android.app.Application
import android.content.Context
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

    @Singleton
    @Provides
    fun provideApiKeyInterceptor(): ApiKeyInterceptor {
        val apiKey = "6cb32867a94de7a19988927b1aece140"
        return ApiKeyInterceptor(apiKey)
    }
    @Singleton
    @Provides
    fun provideRetrofitClient(apiKeyInterceptor: ApiKeyInterceptor): RetrofitClient.RetrofitClient {
        return RetrofitClient.RetrofitClient
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: RetrofitClient.RetrofitClient): ApiService {
        return retrofit.instance
    }


//    @Singleton
//    @Provides
//    fun provideRetrofitClient(apiKeyInterceptor: ApiKeyInterceptor): RetrofitClient.RetrofitClient {
//        return RetrofitClient.RetrofitClient
//    }
//
//    @Singleton
//    @Provides
//    fun provideApiService(retrofitClient: RetrofitClient.RetrofitClient): ApiService {
//        return retrofitClient.instance
//    }
}