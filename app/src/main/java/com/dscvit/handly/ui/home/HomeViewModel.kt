package com.dscvit.handly.ui.home

import androidx.lifecycle.ViewModel
import com.dscvit.handly.model.collection.CreateCollectionRequest
import com.dscvit.handly.model.collection.UpdateCollection
import com.dscvit.handly.repository.AppRepo

class HomeViewModel(private val repo: AppRepo) : ViewModel() {

    fun createCollection(createCollectionRequest: CreateCollectionRequest) =
        repo.createCollection(createCollectionRequest)

    fun getCollections() = repo.getCollections()

    fun updateCollections(updateCollection: UpdateCollection) =
        repo.updateCollection(updateCollection)

}