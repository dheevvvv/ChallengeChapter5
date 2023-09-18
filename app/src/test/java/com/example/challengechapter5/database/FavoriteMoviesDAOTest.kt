package com.example.challengechapter5.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.challengechapter5.database_room.FavoriteMoviesDAO
import com.example.challengechapter5.database_room.FavoriteMoviesData
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class FavoriteMoviesDAOTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()
    @Mock
    private lateinit var favoriteMoviesDAO: FavoriteMoviesDAO

    @Before
    fun setUp() {
        // Inisialisasi Mockito annotations
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testInsert() {
        // Mock data yang akan dimasukkan ke dalam database
        val favoriteMoviesData = FavoriteMoviesData(1, 1, "Batman", "www", 9.1, isFavorite = true )

        //fungsi yang akan diuji
        favoriteMoviesDAO.insert(favoriteMoviesData)

        // Verifikasi bahwa favoriteMoviesDAO.insert() dipanggil dengan parameter yang benar
        Mockito.verify(favoriteMoviesDAO).insert(favoriteMoviesData)
    }

    @Test
    fun testGetFavoriteMoviesByUser() {
        // Mock data yang akan dikembalikan oleh favoriteMoviesDAO.getFavoriteMoviesByUser()
        val userId = 1
        val favoriteMoviesList = listOf(FavoriteMoviesData(1, 1, "Batman", "www", 9.1, isFavorite = true))

        // Mock implementasi favoriteMoviesDAO.getFavoriteMoviesByUser()
        Mockito.`when`(favoriteMoviesDAO.getFavoriteMoviesByUser(userId)).thenReturn(favoriteMoviesList)

        // fungsi yang akan diuji
        val resultFavoriteMoviesList = favoriteMoviesDAO.getFavoriteMoviesByUser(userId)

        // Verifikasi hasilnya
        Mockito.verify(favoriteMoviesDAO).getFavoriteMoviesByUser(userId)
        Assert.assertEquals(favoriteMoviesList, resultFavoriteMoviesList)
    }

    @Test
    fun testDeleteFavoriteMoviesByIdAndUser() {
        // Mock parameter yang akan diterima oleh favoriteMoviesDAO.deleteFavoriteMoviesByIdAndUser()
        val movieId = 1
        val userId = 1

        // fungsi yang akan diuji
        favoriteMoviesDAO.deleteFavoriteMoviesByIdAndUser(movieId, userId)

        // Verifikasi bahwa favoriteMoviesDAO.deleteFavoriteMoviesByIdAndUser() dipanggil dengan parameter yang benar
        Mockito.verify(favoriteMoviesDAO).deleteFavoriteMoviesByIdAndUser(movieId, userId)
    }

    @Test
    fun testDelete() {
        // Mock data yang akan dihapus dari database
        val favoriteMoviesData = FavoriteMoviesData(1, 1, "Batman", "www", 9.1, isFavorite = true)

        // fungsi yang akan diuji
        favoriteMoviesDAO.delete(favoriteMoviesData)

        // Verifikasi bahwa favoriteMoviesDAO.delete() dipanggil dengan parameter yang benar
        Mockito.verify(favoriteMoviesDAO).delete(favoriteMoviesData)
    }

    @Test
    fun testGetIsFavorite() {
        // Mock data yang akan dikembalikan oleh favoriteMoviesDAO.getIsFavorite()
        val movieId = 1
        val isFavorite = true

        // Mock implementasi favoriteMoviesDAO.getIsFavorite()
        Mockito.`when`(favoriteMoviesDAO.getIsFavorite(movieId)).thenReturn(isFavorite)

        // fungsi yang akan diuji
        val resultIsFavorite = favoriteMoviesDAO.getIsFavorite(movieId)

        // Verifikasi hasilnya
        Mockito.verify(favoriteMoviesDAO).getIsFavorite(movieId)
        Assert.assertEquals(isFavorite, resultIsFavorite)
    }
}