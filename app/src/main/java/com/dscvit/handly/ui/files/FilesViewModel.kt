package com.dscvit.handly.ui.files

import androidx.lifecycle.ViewModel
import com.dscvit.handly.model.files.FileViewRequest
import com.dscvit.handly.repository.AppRepo

class FilesViewModel(private val repo: AppRepo) : ViewModel() {
    fun getFiles(fileViewRequest: FileViewRequest) = repo.getFiles(fileViewRequest)
}