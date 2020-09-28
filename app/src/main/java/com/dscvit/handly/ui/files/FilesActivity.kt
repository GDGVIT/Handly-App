package com.dscvit.handly.ui.files

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.dscvit.handly.R
import com.dscvit.handly.model.Result
import com.dscvit.handly.model.files.FileViewRequest
import com.dscvit.handly.util.hide
import com.dscvit.handly.util.show
import com.github.ybq.android.spinkit.style.Wave
import kotlinx.android.synthetic.main.activity_files.*
import org.koin.android.viewmodel.ext.android.viewModel

class FilesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_files)

        collection_progress.hide()
        collection_progress.setIndeterminateDrawable(Wave())

        val fileViewModel by viewModel<FilesViewModel>()

        val extras = intent.extras
        val collectionID = extras?.getString("collectionID")
        val collectionName = extras?.getString("collectionName")

        toolbar?.title = collectionName
        toolbar.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_back)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        getFiles(fileViewModel, collectionID?:"")
    }

    private fun getFiles(filesViewModel: FilesViewModel, id: String) {
        val requestBody = FileViewRequest(id)
        filesViewModel.getFiles(requestBody).observe(this, Observer {
            when (it) {
                is Result.Loading -> {
                    collection_progress.show()
                }
                is Result.Success -> {
                    collection_progress.hide()
                    Log.d("esh", it.toString())
                }
                is Result.Error -> {
                    collection_progress.hide()
                    Log.d("esh", "Error Ono")
                }
            }
        })
    }
}