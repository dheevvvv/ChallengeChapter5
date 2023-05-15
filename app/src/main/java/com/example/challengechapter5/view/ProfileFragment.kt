package com.example.challengechapter5.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.challengechapter5.R
import com.example.challengechapter5.database_room.UserData
import com.example.challengechapter5.databinding.FragmentProfileBinding
import com.example.challengechapter5.datastore_preferences.UserManager
import com.example.challengechapter5.viewmodel.UserViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var userManager: UserManager
    private val userViewModel:UserViewModel by viewModels()
    lateinit var mGoogleSignInClient: GoogleSignInClient

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userManager = UserManager.getInstance(requireContext())

//        userViewModel.getUsername()
//        userViewModel.username.observe(viewLifecycleOwner){
//            binding.tvUsernameProfile.text = it.toString()
//        }
//        userViewModel.getProfilePhoto()
//        userViewModel.profilePhoto.observe(viewLifecycleOwner){
//            Glide.with(this).load(it)
//                .circleCrop()
//                .into(binding.ivProfileImg)
//        }
        userViewModel.getUser().observe(viewLifecycleOwner, Observer {
            binding.tvUsernameProfile.text = it.username
            Glide.with(this).load(it.profilePhoto)
                .circleCrop()
                .into(binding.ivProfileImg)
        })

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        binding.btnEditProfile.setOnClickListener {
            userViewModel.getUser().observe(viewLifecycleOwner, Observer {
                if (it!=null){
                    val user = it
                    val bundle = Bundle()
                    bundle.putSerializable("dataUser", user)
                    findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment, bundle)
                }
            })
//            val id = userViewModel.getUserId().toString().toInt()
//            val user = userViewModel.getUser()
//            val bundle = Bundle()
//            bundle.putInt("id", id)
//            bundle.putString("password", user.)
//            bundle.putString("email", user.email)
//            findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment, bundle)
        }

        binding.btnHome.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_homeFragment)
        }

        binding.btnLogout.setOnClickListener {
            mGoogleSignInClient.signOut().addOnCompleteListener {
                GlobalScope.async {
                    userManager.clearData()
                }
                findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
                Toast.makeText(context, "Logging Out", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun sendUserData(userData: UserData){
        val bundle = Bundle().apply {
            putSerializable("userData", userData)
        }
        findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment, bundle)

    }


}