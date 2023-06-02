package com.example.challengechapter5.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.challengechapter5.database_room.UserDAO
import com.example.challengechapter5.datastore_preferences.UserManager
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
}