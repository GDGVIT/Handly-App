package com.dscvit.handly.network

import com.dscvit.handly.model.auth.AuthResponse
import com.dscvit.handly.model.auth.SigninRequest
import com.dscvit.handly.model.auth.SignupRequest
import com.dscvit.handly.model.collection.*
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {

    @POST("user/create/")
    suspend fun signupUser(@Body signupRequest: SignupRequest): Response<AuthResponse>

    @POST("user/login/")
    suspend fun signinUser(@Body signinRequest: SigninRequest): Response<AuthResponse>

    @POST("core/collections/")
    suspend fun createCollection(@Body createCollectionRequest: CreateCollectionRequest):
            Response<CreateCollectionResponse>

    @GET("core/collections/")
    suspend fun getCollections(): Response<ViewCollectionsResponse>

    @PUT("core/collections/")
    suspend fun updateCollection(@Body updateCollection: UpdateCollection): Response<UpdateCollection>

    @HTTP(method = "DELETE", path = "core/collections/", hasBody = true)
    suspend fun deleteCollection(@Body deleteCollectionRequest: DeleteCollectionRequest): Response<Unit>

}