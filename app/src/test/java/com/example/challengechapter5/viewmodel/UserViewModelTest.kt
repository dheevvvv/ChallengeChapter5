package com.example.challengechapter5.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.challengechapter5.database_room.UserDAO
import com.example.challengechapter5.datastore_preferences.UserManager
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.doAnswer


class UserViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var userDAO: UserDAO

    @Mock
    private lateinit var userManager: UserManager

    private lateinit var viewModel: UserViewModel

    // Coroutine testing variables
    private val testDispatcher = TestCoroutineDispatcher()
    private val testCoroutineScope = TestCoroutineScope(testDispatcher)

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.initMocks(this)
        viewModel = UserViewModel(userDAO, userManager)
    }
    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        @Suppress("DEPRECATION")
        testCoroutineScope.cleanupTestCoroutines()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testGetUsername() = testDispatcher.runBlockingTest {
        // Mock data
        val expectedUsername = "Dheevvv"
        `when`(userManager.getUsername()).thenReturn(expectedUsername)

        // Run the function
        viewModel.getUsername()

        // Advance the dispatcher to execute coroutines
        testDispatcher.advanceUntilIdle()

        // Verify the result
        val actualUsername = viewModel.username.value
        assertEquals(expectedUsername, actualUsername)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testGetEmail() = testDispatcher.runBlockingTest {
        // Mock data
        val expectedEmail = "test@example.com"
        `when`(userManager.getEmail()).thenReturn(expectedEmail)

        // Run the function
        viewModel.getEmail()

        // Advance the dispatcher to execute coroutines
        testDispatcher.advanceUntilIdle()

        // Verify the result
        val actualEmail = viewModel.email.value
        assertEquals(expectedEmail, actualEmail)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testGetProfilePhoto() = testDispatcher.runBlockingTest {
        // Mock data
        val expectedProfilePhoto = "https://example.com/profile.jpg"

        // Stub the userManager.getProfilePhoto() function
        `when`(userManager.getProfilePhoto()).thenReturn(expectedProfilePhoto)

        // Run the function
        viewModel.getProfilePhoto()

        // Advance the dispatcher to execute coroutines
        testDispatcher.advanceUntilIdle()

        // Verify the result
        val actualProfilePhoto = viewModel.profilePhoto.value
        assertEquals(expectedProfilePhoto, actualProfilePhoto)
    }




}