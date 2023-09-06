package com.example.challengechapter5.networking

import com.example.challengechapter5.model.PopularMoviesResponse
import com.example.challengechapter5.model.ResultPopular
import junit.framework.TestCase.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiServiceTest {
    @Mock
    private lateinit var apiService: ApiService

    @Mock
    private lateinit var call: Call<PopularMoviesResponse>

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }


    @Captor
    private lateinit var callbackCaptor: ArgumentCaptor<Callback<PopularMoviesResponse>>


    @Test
    fun testGetPopularMovies() {
        // Mock response
        val response = PopularMoviesResponse(1, listOf(ResultPopular(false,"wdhiwd", listOf(12),1,"en",
            "title1","desc",78.99, "wdwjnfk","6",
            "title1",false,212.2,378)), 10, 10)

        // Create a mock response
        val mockResponse = Response.success(PopularMoviesResponse(1, listOf(ResultPopular(false,"wdhiwd", listOf(12),1,"en",
            "title1","desc",78.99, "wdwjnfk","6",
            "title1",false,212.2,378)), 10, 10))
        // Stub the ApiService.getPopularMovies() function
        `when`(apiService.getPopularMovies()).thenReturn(call)

        // Stub the Call.enqueue() function
        `when`(call.enqueue(any())).thenAnswer {
            val callback = it.arguments[0] as Callback<PopularMoviesResponse>
            callback.onResponse(call, mockResponse)
        }

        // Call the function under test
        val actualCall = apiService.getPopularMovies()
        val callback = mock(Callback::class.java) as Callback<PopularMoviesResponse>
        actualCall.enqueue(callback)

        // Verify the result
        verify(call).enqueue(callback)
        verify(callback).onResponse(call, mockResponse)

    }
}