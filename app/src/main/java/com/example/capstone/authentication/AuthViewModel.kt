package com.example.capstone.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.map

class AuthViewModel : ViewModel(){

    enum class AuthenticationState {
        AUTHENTICATED, UNAUTHENTICATED, INVALID_AUTHENTICATION
    }

    val authState = FirebaseUserLiveData().map { user ->
        if (user != null){
            AuthenticationState.AUTHENTICATED
        }else{
            AuthenticationState.UNAUTHENTICATED
        }
    }

}