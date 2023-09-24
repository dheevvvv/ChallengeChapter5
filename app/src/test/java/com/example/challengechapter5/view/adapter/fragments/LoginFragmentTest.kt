package com.example.challengechapter5.view.adapter.fragments

import com.example.challengechapter5.view.LoginFragment
import org.junit.After
import org.junit.Before
import org.junit.Test

class LoginFragmentTest {

    lateinit var login : LoginFragment

    @Before
    fun setUp() {
        login = LoginFragment()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun usernameIsEmpty() {
        val username = ""
        val assert = login.validate(username, "wddefmoel@pwkdm", "owdjo")
        assert(assert == "please enter username")
    }


    @Test
    fun usernameLengthLessThan6() {
        val username = "dep"
        assert(
            login.validate(username, "dep@gmail", "eiehfief")
                    == "very short username"
        )
    }

    @Test
    fun usernameLengthVeryLong(){
        val username="depppppppppppppppppppppppppppppppppppppppppp"
        assert(
            login.validate(username, "dep@gmail", "edjoedjo")
                    == "long username"
        )
    }

    @Test
    fun emailContains(){
        val email = "deppgmail"
        assert(
            login.validate("depaaa", email, "skdndiwd")
                == "please enter valid email"
        )
    }

}