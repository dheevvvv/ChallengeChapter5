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
        val user = ""
        val result = regist.validateRegistrationInput(user,"12aa&34","12aa&34")
        assertEquals("kosong",result,false)
    }

    @Test
    fun validUsernameAndCorrectlyRepeatedPassword_return_true() {
        val result = regist.validateRegistrationInput(
            "Devvvv",
            "12aa&34",
            "12aa&34"
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

    @Test
    fun testPasswordContainDigitLetterAndSymbol(){
        val password = "depp$99"
        val result = regist.validateRegistrationInput(
            username = "depa",
            password,
            password
        )
        assertEquals(result, true)
    }

}