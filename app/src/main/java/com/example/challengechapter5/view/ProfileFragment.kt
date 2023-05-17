package com.example.challengechapter5.view

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
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


        userViewModel.getUsername()
        userViewModel.username.observe(viewLifecycleOwner, Observer {
            binding.tvUsernameProfile.text = it.toString()
        })


        userViewModel.getProfilePhoto()
        userViewModel.profilePhoto.observe(viewLifecycleOwner, Observer {
//            val byteArray = Base64.decode(it, Base64.DEFAULT)
            val byteArray = base64ToBitmap(it)
            Glide.with(this).load(byteArray)
                .circleCrop()
                .into(binding.ivProfileImg)
        })
//        userViewModel.profilePhoto.observe(viewLifecycleOwner, Observer { photoProfile ->
//            val byteArray = Base64.decode(photoProfile, Base64.DEFAULT)
//            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
//            userViewModel.setProfilePhotoBitmap(bitmap)
//
//        })
//
//        userViewModel.profilePhotoBitmap.observe(viewLifecycleOwner, Observer { bitmap ->
//            if (bitmap != null) {
//                Glide.with(requireContext())
//                    .load(bitmap)
//                    .circleCrop()
//                    .into(binding.ivProfileImg)
//            } else {
//                //
//            }
//        })


//        userViewModel.profilePhoto.observe(viewLifecycleOwner, Observer { photoProfile ->
//            val byteArray = Base64.decode(photoProfile, Base64.DEFAULT)
//            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
//            if (bitmap != null) {
//                Glide.with(requireContext())
//                    .load(bitmap)
//                    .circleCrop()
//                    .into(binding.ivProfileImg)
//            } else {
//                //
//
//            }
//        })


//        userViewModel.profilePhoto.observe(viewLifecycleOwner, Observer {
//            val byteArray = Base64.decode(it, Base64.DEFAULT)
//            Glide.with(this).load(byteArray)
//                .circleCrop()
//                .into(binding.ivProfileImg)
//        })
        //            val bitmap = userViewModel.getBitmapFromProfilePhoto(it)

//        userViewModel.getUser().observe(viewLifecycleOwner, Observer {
//            binding.tvUsernameProfile.text = it.username
//            Glide.with(this).load(it.profilePhoto)
//                .circleCrop()
//                .into(binding.ivProfileImg)
//        })

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
            mGoogleSignInClient.signOut()
            GlobalScope.async {
                userManager.clearData()
            }
            findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
            Toast.makeText(context, "Logging Out", Toast.LENGTH_SHORT).show()
        }
    }

    private fun base64ToBitmap(encodedImage: String): Bitmap? {
        val decodedByteArray = android.util.Base64.decode(encodedImage, android.util.Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.size)
    }





}