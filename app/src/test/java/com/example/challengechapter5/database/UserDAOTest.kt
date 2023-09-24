package com.example.challengechapter5.database

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.datastore.core.IOException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.challengechapter5.database_room.MovieDatabase
import com.example.challengechapter5.database_room.UserDAO
import com.example.challengechapter5.database_room.UserData
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import javax.inject.Inject
import javax.inject.Named



class UserDAOTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var userDao: UserDAO

    @Before
    fun setUp() {
        // Inisialisasi Mockito annotations
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testInsertAndGetUser() {
        val userData = UserData(1, "testdepp", "depa@gmail.com", "depdep", "widfinei")

        Mockito.doNothing().`when`(userDao).insertUser(userData)


        val liveData = MutableLiveData<UserData>()
        liveData.value = userData
        Mockito.`when`(userDao.getUser()).thenReturn(liveData)

        val userDAO = userDao

        userDAO.insertUser(userData)
        val observedUserData = userDAO.getUser().value

        Assert.assertEquals(userData, observedUserData)
    }

    @Test
    fun testGetUser() {
        // Mock data yang akan dikembalikan oleh userDao.getUser()
        val userData = UserData(1, "testdepp", "depa@gmail.com", "depdep", "widfinei")
        val liveData = MutableLiveData<UserData>()
        liveData.value = userData

        // Mock implementasi userDao.getUser()
        Mockito.`when`(userDao.getUser()).thenReturn(liveData)

        //fungsi yang akan diuji
        val resultLiveData = userDao.getUser()

        // Verifikasi hasil
        Mockito.verify(userDao).getUser()
        Assert.assertEquals(userData, resultLiveData.value)
    }

    @Test
    fun testCheckUser() {
        // Mock data yang akan dikembalikan oleh userDao.checkUser()
        val email = "test@example.com"
        val password = "password"
        val userData = UserData(1, "testdepp", "depa@gmail.com", "depdep", "widfinei")
        val liveData = MutableLiveData<UserData>()
        liveData.value = userData

        // Mock implementasi userDao.checkUser()
        Mockito.`when`(userDao.checkUser(email, password)).thenReturn(liveData)

        //  fungsi yang akan diuji
        val resultLiveData = userDao.checkUser(email, password)

        // Verifikasi hasil
        Mockito.verify(userDao).checkUser(email, password)
        Assert.assertEquals(userData, resultLiveData.value)
    }

    @Test
    fun testUpdateUser() {
        // Mock parameter yang akan diterima oleh userDao.updateUser()
        val userData = UserData(1, "testdepp", "depa@gmail.com", "depdep", "widfinei")

        // fungsi yang akan diuji
        userDao.updateUser(userData)

        // Verifikasi bahwa userDao.updateUser() dipanggil dengan parameter yang benar
        Mockito.verify(userDao).updateUser(userData)
    }

    @Test
    fun testGetUserIdByEmail() {
        // Mock data yang akan dikembalikan oleh userDao.getUserIdByEmail()
        val email = "test@example.com"
        val userId = 1

        // Mock implementasi userDao.getUserIdByEmail()
        Mockito.`when`(userDao.getUserIdByEmail(email)).thenReturn(userId)

        // fungsi yang akan diuji
        val resultUserId = userDao.getUserIdByEmail(email)

        // Verifikasi hasil
        Mockito.verify(userDao).getUserIdByEmail(email)
        Assert.assertEquals(userId, resultUserId)
    }
}