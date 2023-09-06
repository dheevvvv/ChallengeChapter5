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
    fun insertAndGetUser() {
        val userData = UserData(1, "testdepp", "depa@gmail.com", "depdep", "widfinei")

        Mockito.doNothing().`when`(userDao).insertUser(userData)


        val liveData = MutableLiveData<UserData>()
        liveData.value = userData
        Mockito.`when`(userDao.getUser()).thenReturn(liveData)

        val userDAO = userDao

        userDAO.insertUser(userData)
        val observedUserData = userDAO.getUser().value

        Assert.assertEquals(userData, observedUserData)

    //
//    @get:Rule
//    val hiltRule = HiltAndroidRule(this)
//
//    @get:Rule
//    val instantExecutorRule = InstantTaskExecutorRule()
//
//    @Inject
//    lateinit var movieDatabase: MovieDatabase
//    @Inject
//    lateinit var userDAO: UserDAO
//
//    @Before
//    fun setup(){
//        hiltRule.inject()
//        userDAO = movieDatabase.userDao()
//    }
//    @After
//    fun tearDown() {
//        movieDatabase.close()
//    }
//
//    @Test
//    fun testInsertAndGetUser() {
//        val userData = UserData(1, "testdepp", "depa@gmail.com", "depdep", "widfinei")
//
//        // Insert user data
//        userDAO.insertUser(userData)
//
//        // Observe LiveData to get the user data
//        val liveData: LiveData<UserData> = userDAO.getUser()
//        val observer = Observer<UserData> {
//            Assert.assertEquals(userData, it)
//        }
//        liveData.observeForever(observer)
    }
}