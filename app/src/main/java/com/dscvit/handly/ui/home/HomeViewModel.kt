package com.dscvit.handly.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dscvit.handly.model.collection.CreateCollectionRequest
import com.dscvit.handly.model.collection.DeleteCollectionRequest
import com.dscvit.handly.model.collection.UpdateCollection
import com.dscvit.handly.repository.AppRepo
import kotlinx.coroutines.Dispatchers

class HomeViewModel(private val repo: AppRepo) : ViewModel() {

    fun createCollection(createCollectionRequest: CreateCollectionRequest) =
        repo.createCollection(createCollectionRequest)

    fun getCollections() = repo.getCollections()

    fun updateCollections(updateCollection: UpdateCollection) =
        repo.updateCollection(updateCollection)

    fun deleteCollection(deleteCollectionRequest: DeleteCollectionRequest): LiveData<String> =
        liveData(Dispatchers.IO) {
            emit("Loading")

            try {
                repo.deleteCollection(deleteCollectionRequest)
                emit("Success")
            } catch (e: Exception) {
                emit("Failed")
            }
        }
}