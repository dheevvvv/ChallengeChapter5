package com.example.challengechapter5.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.UserManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.bumptech.glide.Glide
import com.example.challengechapter5.R
import com.example.challengechapter5.database_room.FavoriteMoviesData
import com.example.challengechapter5.databinding.FragmentDetailMovieBinding
import com.example.challengechapter5.viewmodel.FavoriteViewModel
import com.example.challengechapter5.viewmodel.MovieViewModel
import com.example.challengechapter5.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class DetailMovieFragment : Fragment() {
    private lateinit var binding: FragmentDetailMovieBinding
    private lateinit var viewModel: MovieViewModel
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var id:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailMovieBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
        viewModel = ViewModelProvider(requireActivity()).get(MovieViewModel::class.java)
        favoriteViewModel = ViewModelProvider(requireActivity()).get(FavoriteViewModel::class.java)
        val dataId = arguments?.getInt("id")
        val title = arguments?.getString("title")
        val posterPath = arguments?.getString("posterpath")
        val voteAverage = arguments?.getDouble("voteaverage")

        userViewModel.getEmail()
        userViewModel.email.observe(viewLifecycleOwner, Observer {email->
            userViewModel.getUserId(email)
            userViewModel.userId.observe(viewLifecycleOwner, Observer {
                val userId = it
                binding.btnAddtoFavorite.setOnClickListener {
                    binding.ivFavoriteStar.setImageResource(R.drawable.ic_favoriteyellow)
                    favoriteViewModel.insertFavoriteMovies(FavoriteMoviesData(movieId = dataId!!.toInt(), userId = userId, title = title.toString(), posterPath = posterPath.toString(), voteAverage = voteAverage!!))
                }
            })
        })



        if (dataId != null) {
            id = dataId.toString()
        }
        getDetailMovie()
        getDetailTvSeries()


    }

    @SuppressLint("SetTextI18n")
    fun getDetailMovie(){
        viewModel.callGetDetailMovie(id.toInt())
        viewModel.movieDetail.observe(viewLifecycleOwner, Observer {
            if (it != null){
                Glide.with(binding.root)
                    .load("https://image.tmdb.org/t/p/w500/${it.posterPath}")
                    .into(binding.ivMoviePosterImage)
                binding.tvMovieTitle.text = it.title
                binding.tvMovieTagline.text = "Tagline :" + "  " + it.tagline
                binding.tvMovieDuration.text = it.runtime.toString() + "  " + "minutes"
                binding.tvMovieReleaseDate.text = it.releaseDate
                binding.tvMovieOverview.text = it.overview
            }
        })
    }

    @SuppressLint("SetTextI18n")
    fun getDetailTvSeries(){
        viewModel.callGetTvSeriesDetail(id.toInt())
        viewModel.tvSeriesDetail.observe(viewLifecycleOwner, Observer {
            if (it != null){
                Glide.with(binding.root)
                    .load("https://image.tmdb.org/t/p/w500/${it.posterPath}")
                    .into(binding.ivMoviePosterImage)
                binding.tvMovieTitle.text = it.name
                binding.tvMovieTagline.text = it.numberOfSeasons.toString() + "  " + "Seasons"
                binding.tvMovieDuration.text = it.numberOfEpisodes.toString() + "  "  +  "episodes"
                binding.tvMovieReleaseDate.text = it.firstAirDate
                binding.tvMovieOverview.text = it.overview
            }
        })
    }


}