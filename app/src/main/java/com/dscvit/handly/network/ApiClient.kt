package com.dscvit.handly.network

import com.dscvit.handly.model.auth.SigninRequest
import com.dscvit.handly.model.auth.SignupRequest

class ApiClient(private val api: ApiInterface): BaseApiClient() {

    suspend fun signupUser(signupRequest: SignupRequest) = getResult {
        api.signupUser(signupRequest)
    }

    suspend fun signinUser(signinRequest: SigninRequest) = getResult {
        api.signinUser(signinRequest)
    }

}