@file:Suppress("RedundantNullableReturnType")

package com.example.challengechapter5.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challengechapter5.R
import com.example.challengechapter5.databinding.FragmentHomeBinding
import com.example.challengechapter5.view.adapter.MovieAdapter
import com.example.challengechapter5.viewmodel.MovieViewModel
import com.example.challengechapter5.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint


@Suppress("RedundantSamConstructor", "NestedLambdaShadowedImplicitParameter")
@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val movieViewModel:MovieViewModel by viewModels()
    private val userViewModel:UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        userViewModel.getUsername()
        userViewModel.username.observe(viewLifecycleOwner){
            binding.tvHome.text = getString(R.string.hello_welcome) + it
        }

        showDataPopularMovie()
        showdataTopRatedMovies()
        showDataUpcomingMovies()
        showDataPopularTvSeries()
        showDataTopRatedTvSeries()

        binding.ivProfile.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }
    }

    private fun showDataPopularMovie(){
        movieViewModel.callGetPopularMovies()
        movieViewModel.popularMovies.observe(viewLifecycleOwner, Observer {
            if (it != null){
                val adapter = MovieAdapter(it)
                binding.rvPopularMovies.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                binding.rvPopularMovies.adapter = adapter
                adapter.onClick= {
                    val id = it.id
                    val bundle = Bundle()
                    bundle.putInt("id", id)
                    bundle.putString("title", it.title)
                    bundle.putString("posterpath", it.posterPath)
                    bundle.putDouble("voteaverage", it.voteAverage)
                    findNavController().navigate(R.id.action_homeFragment_to_detailMovieFragment, bundle)
                }
            }
            else{
                Toast.makeText(context, "null", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showdataTopRatedMovies(){
        movieViewModel.callGetTopRatedMovies()
        movieViewModel.movieTopRated.observe(viewLifecycleOwner, Observer {
            if (it != null){
                val adapter = MovieAdapter(it)
                binding.rvTopRatedMovies.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                binding.rvTopRatedMovies.adapter = adapter
                adapter.onClick= {
                    val id = it.id
                    val bundle = Bundle()
                    bundle.putInt("id", id)
                    bundle.putString("title", it.title)
                    bundle.putString("posterpath", it.posterPath)
                    bundle.putString("voteaverage", it.voteAverage.toString())
                    findNavController().navigate(R.id.action_homeFragment_to_detailMovieFragment, bundle)
                }
            }
        })
    }

    private fun showDataUpcomingMovies(){
        movieViewModel.callGetUpcomingMovies()
        movieViewModel.upcomingMovies.observe(viewLifecycleOwner, Observer {
            if (it != null){
                val adapter = MovieAdapter(it)
                binding.rvUpcomingMovies.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                binding.rvUpcomingMovies.adapter = adapter
                adapter.onClick= {
                    val id = it.id
                    val bundle = Bundle()
                    bundle.putInt("id", id)
                    bundle.putString("title", it.title)
                    bundle.putString("posterpath", it.posterPath)
                    bundle.putString("voteaverage", it.voteAverage.toString())
                    findNavController().navigate(R.id.action_homeFragment_to_detailMovieFragment, bundle)
                }
            }
        })
    }

    private fun showDataPopularTvSeries(){
        movieViewModel.callGetTvSeriesPopular()
        movieViewModel.popularTvSeries.observe(viewLifecycleOwner, Observer {
            if (it!=null){
                val adapter = MovieAdapter(it)
                binding.rvPopularTvSeries.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                binding.rvPopularTvSeries.adapter = adapter
                adapter.onClick= {
                    val id = it.id
                    val bundle = Bundle()
                    bundle.putInt("id", id)
                    bundle.putString("title", it.title)
                    bundle.putString("posterpath", it.posterPath)
                    bundle.putString("voteaverage", it.voteAverage.toString())
                    findNavController().navigate(R.id.action_homeFragment_to_detailMovieFragment, bundle)
                }
            }
        })
    }

    private fun showDataTopRatedTvSeries(){
        movieViewModel.callGetTvSeriesTopRated()
        movieViewModel.topRatedTvSeries.observe(viewLifecycleOwner, Observer {
            if (it!=null){
                val adapter = MovieAdapter(it)
                binding.rvTopratedTvSeries.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                binding.rvTopratedTvSeries.adapter = adapter
                adapter.onClick= {
                    val id = it.id
                    val bundle = Bundle()
                    bundle.putInt("id", id)
                    bundle.putString("title", it.title)
                    bundle.putString("posterpath", it.posterPath)
                    bundle.putString("voteaverage", it.voteAverage.toString())
                    findNavController().navigate(R.id.action_homeFragment_to_detailMovieFragment, bundle)
                }
            }
        })
    }


}