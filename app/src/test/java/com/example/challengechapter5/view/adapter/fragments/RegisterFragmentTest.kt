package com.example.challengechapter5.view.adapter.fragments

import com.example.challengechapter5.view.RegisterFragment
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class RegisterFragmentTest {

    lateinit var regist : RegisterFragment

    @Before
    fun setUp(){
        regist = RegisterFragment()
    }

    @Test
    fun passwordEmpty_false(){
        val result = regist.validateRegistrationInput("depp","","12345")
        assertEquals("password empty", result, false)
    }

    @Test
    fun usernameEmpty(){
        val user = "depa"
        val result = regist.validateRegistrationInput(user,"1234","1234")
        assertEquals("kosong",result,true)
    }

    @Test
    fun validUsernameAndCorrectlyRepeatedPassword_return_true() {
        val result = regist.validateRegistrationInput(
            "Devv",
            "12345",
            "12345"
        )
        assertEquals("oke",result,true)
    }

    @Test
    fun testPasswordUnder2Digit(){
        val result = regist.validateRegistrationInput(
            "depp",
            "1",
            "1"
        )
        assertEquals(result, false)
    }


}