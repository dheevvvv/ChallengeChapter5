package com.example.challengechapter5.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.challengechapter5.R
import com.example.challengechapter5.database_room.UserData
import com.example.challengechapter5.databinding.FragmentEditProfileBinding
import com.example.challengechapter5.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth


class EditProfileFragment : Fragment() {
    private lateinit var binding:FragmentEditProfileBinding
    private lateinit var userViewModel: UserViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
        binding.btnSaveEdit.setOnClickListener {
            saveEdit()
            findNavController().navigate(R.id.action_editProfileFragment_to_profileFragment)
        }

    }

//    fun saveEdit(){
////        val firebaseUser = FirebaseAuth.getInstance().currentUser
////        val id = firebaseUser?.uid.toString()
////        val id = arguments?.getInt("id")
//        val dataUser = arguments?.getSerializable("dataUser") as UserData
//        val imgProfile = binding.etImageProfile.text.toString()
//        val username = binding.etUsernameProfile.text.toString()
//        userViewModel.updateUser(UserData(id = dataUser.id, username = username, password = dataUser.password, email = dataUser.email, profilePhoto = imgProfile))
//        Toast.makeText(requireContext(), "save edit profile berhasil", Toast.LENGTH_SHORT).show()
//    }

    fun saveEdit() {
        val dataUser = arguments?.getSerializable("dataUser") as UserData
        val imgProfile = binding.etImageProfile.text.toString()
        val username = binding.etUsernameProfile.text.toString()


        if (imgProfile.isNotEmpty() || username.isNotEmpty()) {

            val updatedUser = UserData(
                id = dataUser.id,
                username = if (username.isNotEmpty()) username else dataUser.username,
                password = dataUser.password,
                email = dataUser.email,
                profilePhoto = if (imgProfile.isNotEmpty()) imgProfile else dataUser.profilePhoto
            )
            userViewModel.updateUser(updatedUser)
            Toast.makeText(requireContext(), "Edit profile berhasil disimpan", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Tidak ada perubahan yang disimpan", Toast.LENGTH_SHORT).show()
        }
    }


}