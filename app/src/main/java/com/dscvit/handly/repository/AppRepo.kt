package com.dscvit.handly.repository

import com.dscvit.handly.model.auth.SigninRequest
import com.dscvit.handly.model.auth.SignupRequest
import com.dscvit.handly.model.collection.CreateCollectionRequest
import com.dscvit.handly.model.collection.DeleteCollectionRequest
import com.dscvit.handly.model.collection.UpdateCollection
import com.dscvit.handly.model.files.FileViewRequest
import com.dscvit.handly.model.files.UpdateFile
import com.dscvit.handly.network.ApiClient
import okhttp3.MultipartBody
import okhttp3.RequestBody

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

    fun getFiles(fileViewRequest: FileViewRequest) = makeRequest {
        apiClient.getFiles(fileViewRequest)
    }

    fun updateFile(id: String, updateFile: UpdateFile) = makeRequest {
        apiClient.updateFile(id, updateFile)
    }

    suspend fun deleteFile(id: String) = apiClient.deleteFile(id)

    fun uploadFile(collectionID: RequestBody, name: RequestBody, file: MultipartBody.Part) =
        makeRequest {
            apiClient.uploadFile(collectionID, name, file)
        }

    suspend fun deleteCollection(deleteCollectionRequest: DeleteCollectionRequest) =
        apiClient.deleteCollection(deleteCollectionRequest)

}