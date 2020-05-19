package com.dscvit.handly.repository

import com.dscvit.handly.model.auth.SigninRequest
import com.dscvit.handly.model.auth.SignupRequest
import com.dscvit.handly.network.ApiClient

class AppRepo(private val apiClient: ApiClient): BaseRepo() {

    fun signupUser(signupRequest: SignupRequest) = makeRequest {
        apiClient.signupUser(signupRequest)
    }

    fun signinUser(signinRequest: SigninRequest) = makeRequest {
        apiClient.signinUser(signinRequest)
    }

}