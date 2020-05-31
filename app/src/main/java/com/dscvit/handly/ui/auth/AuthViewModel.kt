package com.dscvit.handly.ui.auth

import androidx.lifecycle.ViewModel
import com.dscvit.handly.model.auth.SigninRequest
import com.dscvit.handly.model.auth.SignupRequest
import com.dscvit.handly.repository.AppRepo

class AuthViewModel(private val repo: AppRepo): ViewModel() {

    fun signupUser(signupRequest: SignupRequest) = repo.signupUser(signupRequest)

    fun signinUser(signinRequest: SigninRequest) = repo.signinUser(signinRequest)

}