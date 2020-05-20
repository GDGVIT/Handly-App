package com.dscvit.handly.repository

import com.dscvit.handly.model.auth.SigninRequest
import com.dscvit.handly.model.auth.SignupRequest
import com.dscvit.handly.model.collection.CreateCollectionRequest
import com.dscvit.handly.model.collection.DeleteCollectionRequest
import com.dscvit.handly.model.collection.UpdateCollection
import com.dscvit.handly.network.ApiClient

class AppRepo(private val apiClient: ApiClient) : BaseRepo() {

    fun signupUser(signupRequest: SignupRequest) = makeRequest {
        apiClient.signupUser(signupRequest)
    }

    fun signinUser(signinRequest: SigninRequest) = makeRequest {
        apiClient.signinUser(signinRequest)
    }

    fun createCollection(createCollectionRequest: CreateCollectionRequest) = makeRequest {
        apiClient.createCollection(createCollectionRequest)
    }

    fun getCollections() = makeRequest {
        apiClient.getCollections()
    }

    fun updateCollection(updateCollection: UpdateCollection) = makeRequest {
        apiClient.updateCollection(updateCollection)
    }

    suspend fun deleteCollection(deleteCollectionRequest: DeleteCollectionRequest) =
        apiClient.deleteCollection(deleteCollectionRequest)

}