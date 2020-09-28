package com.dscvit.handly.ui.files

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dscvit.handly.model.files.FileViewRequest
import com.dscvit.handly.model.files.UpdateFile
import com.dscvit.handly.repository.AppRepo
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class FilesViewModel(private val repo: AppRepo) : ViewModel() {
    fun getFiles(fileViewRequest: FileViewRequest) = repo.getFiles(fileViewRequest)

    fun updateFile(id: String, updateFile: UpdateFile) = repo.updateFile(id, updateFile)

    fun deleteFile(id: String): LiveData<String> = liveData (Dispatchers.IO) {
        emit("Loading")

        try {
            repo.deleteFile(id)
            emit("Success")
        } catch (e: Exception) {
            emit("Failed")
            Log.d("esh", e.toString())
        }
    }
}