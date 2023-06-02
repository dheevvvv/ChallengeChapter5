package com.example.challengechapter5.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.challengechapter5.model.ResultPopular
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import io.mockk.verify
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock


class MovieAdapterTest {
    @MockK
    private lateinit var listMovie : List<ResultPopular>
    @MockK
    private lateinit var movieAdapter : MovieAdapter
    @MockK
    private lateinit var onClickListener: (ResultPopular) -> Unit

    @Before
    fun setup(){
        MockKAnnotations.init(this)

        movieAdapter  = MovieAdapter(emptyList())
    }

    @Test
    fun testItemCount(){
        every {
            listMovie.size
        } returns 3

        movieAdapter = MovieAdapter(listMovie)

        val itemCountList = movieAdapter.itemCount

        Assert.assertEquals(3, itemCountList)
    }


    @After
    fun tearDown() {
        unmockkAll()
    }


}
