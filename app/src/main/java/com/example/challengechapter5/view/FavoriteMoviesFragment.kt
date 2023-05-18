package com.example.challengechapter5.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.challengechapter5.R
import com.example.challengechapter5.databinding.FragmentFavoriteMoviesBinding
import com.example.challengechapter5.datastore_preferences.UserManager
import com.example.challengechapter5.view.adapter.FavoriteMoviesAdapter
import com.example.challengechapter5.viewmodel.FavoriteViewModel
import com.example.challengechapter5.viewmodel.MovieViewModel
import com.example.challengechapter5.viewmodel.UserViewModel


class FavoriteMoviesFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteMoviesBinding
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var userManager: UserManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteMoviesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userManager = UserManager.getInstance(requireContext())
        favoriteViewModel = ViewModelProvider(requireActivity()).get(FavoriteViewModel::class.java)
        userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
        movieViewModel = ViewModelProvider(requireActivity()).get(MovieViewModel::class.java)
        showAllFavoriteMovies()

        binding.ivHome.setOnClickListener {
            findNavController().navigate(R.id.action_favoriteMoviesFragment_to_homeFragment)
        }
        binding.ivProfile.setOnClickListener {
            findNavController().navigate(R.id.action_favoriteMoviesFragment_to_profileFragment)
        }

    }

    fun showAllFavoriteMovies(){
        userViewModel.getEmail()
        userViewModel.email.observe(viewLifecycleOwner, Observer {email->
            userViewModel.getUserId(email)
            userViewModel.userId.observe(viewLifecycleOwner, Observer {
                favoriteViewModel.getFavoriteMovies(it)
            })

        })
//        userViewModel.email.observe(viewLifecycleOwner, Observer {email->
//            val userId = userViewModel.getUserId(email.toString())
//            favoriteViewModel.getFavoriteMovies(userId)
//        })


        favoriteViewModel.favoriteMovies.observe(viewLifecycleOwner, Observer {
            if (it != null){
                val adapter = FavoriteMoviesAdapter(it)
                binding.rvFavoriteMovies.layoutManager = GridLayoutManager(context, 2)
                binding.rvFavoriteMovies.adapter = adapter
                adapter.onClick = {
                    val id = it.movieId
                    val bundle = Bundle()
                    bundle.putInt("id", id)
                    findNavController().navigate(R.id.action_favoriteMoviesFragment_to_detailMovieFragment, bundle)
                }
            }
            else{
                Toast.makeText(context, "null", Toast.LENGTH_SHORT).show()
            }
        })
    }


}