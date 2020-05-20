package com.dscvit.handly.network

import com.dscvit.handly.model.auth.SigninRequest
import com.dscvit.handly.model.auth.SignupRequest
import com.dscvit.handly.model.collection.CreateCollectionRequest
import com.dscvit.handly.model.collection.DeleteCollectionRequest
import com.dscvit.handly.model.collection.UpdateCollection

class ApiClient(private val api: ApiInterface) : BaseApiClient() {

    suspend fun signupUser(signupRequest: SignupRequest) = getResult {
        api.signupUser(signupRequest)
    }

    suspend fun signinUser(signinRequest: SigninRequest) = getResult {
        api.signinUser(signinRequest)
    }

    suspend fun createCollection(createCollectionRequest: CreateCollectionRequest) = getResult {
        api.createCollection(createCollectionRequest)
    }

    suspend fun getCollections() = getResult {
        api.getCollections()
    }

    suspend fun updateCollection(updateCollection: UpdateCollection) = getResult {
        api.updateCollection(updateCollection)
    }

    suspend fun deleteCollection(deleteCollectionRequest: DeleteCollectionRequest) =
        api.deleteCollection(deleteCollectionRequest)

}