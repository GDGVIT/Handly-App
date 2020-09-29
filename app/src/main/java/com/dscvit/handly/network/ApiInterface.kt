package com.dscvit.handly.network

import com.dscvit.handly.model.auth.AuthResponse
import com.dscvit.handly.model.auth.SigninRequest
import com.dscvit.handly.model.auth.SignupRequest
import com.dscvit.handly.model.collection.*
import com.dscvit.handly.model.collection.Collection
import com.dscvit.handly.model.files.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*
import java.io.File

interface ApiInterface {

    @POST("user/create/")
    suspend fun signupUser(@Body signupRequest: SignupRequest): Response<AuthResponse>

    @POST("user/login/")
    suspend fun signinUser(@Body signinRequest: SigninRequest): Response<AuthResponse>

    @POST("core/collections/")
    suspend fun createCollection(@Body createCollectionRequest: CreateCollectionRequest):
            Response<CreateCollectionResponse>

    @GET("core/collections/")
    suspend fun getCollections(): Response<ArrayList<Collection>>


    @PUT("core/collections/")
    suspend fun updateCollection(@Body updateCollection: UpdateCollection): Response<UpdateCollection>

    @HTTP(method = "DELETE", path = "core/collections/", hasBody = true)
    suspend fun deleteCollection(@Body deleteCollectionRequest: DeleteCollectionRequest): Response<Unit>

    @POST("core/view/")
    suspend fun getFiles(@Body fileViewRequest: FileViewRequest): Response<List<FileViewResponse>>

    @PATCH("core/update/{id}/")
    suspend fun updateFile(
        @Path(value = "id") id: String,
        @Body updateFile: UpdateFile
    ): Response<UpdateFileResponse>

    @Multipart
    @POST("core/handwriter/")
    suspend fun uploadFile(
        @Part("collection") collectionID: RequestBody,
        @Part("name") name: RequestBody,
        @Part file: MultipartBody.Part
    ): Response<UploadFileResponse>

    @HTTP(method = "DELETE", path = "core/update/{id}/")
    suspend fun deleteFile(@Path(value = "id") id: String): Response<Unit>

}