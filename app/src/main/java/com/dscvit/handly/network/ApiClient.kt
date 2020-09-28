package com.dscvit.handly.network

import com.dscvit.handly.model.auth.SigninRequest
import com.dscvit.handly.model.auth.SignupRequest
import com.dscvit.handly.model.collection.CreateCollectionRequest
import com.dscvit.handly.model.collection.DeleteCollectionRequest
import com.dscvit.handly.model.collection.UpdateCollection
import com.dscvit.handly.model.files.FileViewRequest
import com.dscvit.handly.model.files.UpdateFile

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

    suspend fun getFiles(fileViewRequest: FileViewRequest) = getResult {
        api.getFiles(fileViewRequest)
    }

    suspend fun updateFile(id: String, updateFile: UpdateFile) = getResult {
        api.updateFile(id, updateFile)
    }

    suspend fun deleteFile(id: String) = api.deleteFile(id)

    suspend fun deleteCollection(deleteCollectionRequest: DeleteCollectionRequest) =
        api.deleteCollection(deleteCollectionRequest)

}