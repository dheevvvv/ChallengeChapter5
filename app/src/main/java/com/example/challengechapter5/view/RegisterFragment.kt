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
import com.example.challengechapter5.databinding.FragmentRegisterBinding
import com.example.challengechapter5.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint


@Suppress("RedundantNullableReturnType")
@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding

    lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @Suppress("ReplaceGetOrSet")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
        binding.btnRegister.setOnClickListener {
            register()
        }
    }

    fun saveUser(username : String, email : String, password : String, @Suppress("SameParameterValue") profile_photo:String){
        userViewModel.insertUser(UserData(0,username,email,password, profile_photo))
    }


    fun register(){
        val username = binding.etUsernameRegist.text.toString()
        val email = binding.etEmailRegist.text.toString()
        val password = binding.etPasswordRegist.text.toString()
        val confirmPassword = binding.etConfirmPasswordRegist.text.toString()

        if (password != confirmPassword){
            binding.etConfirmPasswordRegist.error = "Confirm password tidak sama dengan password"
            binding.etConfirmPasswordRegist.requestFocus()

        } else{

            if (validateRegistrationInput(username, password, confirmPassword) == true){
                saveUser(username,email,password, "https://cdn-icons-png.flaticon.com/512/6522/6522516.png")
                Toast.makeText(context, "Registrasi Berhasil", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            } else{
                Toast.makeText(context, "password harus sesuai", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun validateRegistrationInput(
        username: String,
        password: String,
        confirmedPassword: String
    ): Boolean {
        if (username.isEmpty() || password.isEmpty() || confirmedPassword.isEmpty()){
            return false
        }
        if (password != confirmedPassword){
            return false
        }
        if (password.count { it.isDigit() } < 2){
            return false
        }
        val digitCount = password.count { it.isDigit() }
        val letterCount = password.count { it.isLetter() }
        val symbolCount = password.count { !it.isLetterOrDigit() }

        if (digitCount < 1 || letterCount < 1 || symbolCount < 1){
            return false
        }

        return true
    }
}


