package com.example.challengechapter5.view

import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.challengechapter5.R
import com.example.challengechapter5.database_room.UserData
import com.example.challengechapter5.databinding.FragmentEditProfileBinding
import com.example.challengechapter5.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import java.io.ByteArrayOutputStream


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
        }
        binding.btnCamera.setOnClickListener {
            intentCamera()
        }
        binding.btnGalery.setOnClickListener {
            intentGallery()
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
            if (username.isNotEmpty()) userViewModel.updateUsername(username) else dataUser.username
            if (imgProfile.isNotEmpty()) userViewModel.updatePhotoProfile(imgProfile) else dataUser.profilePhoto
            Toast.makeText(requireContext(), "Edit profile berhasil disimpan", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_editProfileFragment_to_profileFragment)
        } else {
            Toast.makeText(requireContext(), "Tidak ada perubahan yang disimpan", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_editProfileFragment_to_profileFragment)
        }

    }

    private fun intentCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(cameraIntent, 1)
        } catch (exception: ActivityNotFoundException) {
            // some error to be shown here
        }
    }

    private fun intentGallery() {
        val galleryIntent = Intent(
            Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(galleryIntent, 2)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            val bitmap = data?.extras?.get("data") as Bitmap
            val encodedImage = bitmapToBase64(bitmap)
            val previewDecode = base64ToBitmap(encodedImage)
            Glide.with(this).load(previewDecode)
                .circleCrop()
                .into(binding.ivEditPhoto)
            binding.etImageProfile.setText(encodedImage)
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            val imageUri = data?.data
            val bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, imageUri)
            val encodedImage = bitmapToBase64(bitmap)
            val previewDecode = base64ToBitmap(encodedImage)
            Glide.with(this).load(previewDecode)
                .circleCrop()
                .into(binding.ivEditPhoto)
            binding.etImageProfile.setText(encodedImage)
        } else {
           //
        }
    }

    private fun bitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }
    private fun base64ToBitmap(encodedImage: String): Bitmap? {
        val decodedByteArray = android.util.Base64.decode(encodedImage, android.util.Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.size)
    }



}