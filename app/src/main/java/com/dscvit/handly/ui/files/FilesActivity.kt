package com.dscvit.handly.ui.files

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.dscvit.handly.R
import com.dscvit.handly.adapter.FilesAdapter
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

        file_progress.hide()
        file_progress.setIndeterminateDrawable(Wave())

        val fileViewModel by viewModel<FilesViewModel>()

        val filesAdapter = FilesAdapter()
        filesRecyclerView.apply {
            layoutManager =
                GridLayoutManager(this@FilesActivity, 3, GridLayoutManager.VERTICAL, false)
            adapter = filesAdapter
        }

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
                    filesRecyclerView.hide()
                    file_progress.show()
                }
                is Result.Success -> {
                    filesRecyclerView.show()
                    file_progress.hide()
                    Log.d("esh", it.toString())
                }
                is Result.Error -> {
                    filesRecyclerView.hide()
                    file_progress.hide()
                    Log.d("esh", "Error Ono")
                }
            }
        })
    }
}