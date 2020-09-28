package com.dscvit.handly.ui.files

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.dscvit.handly.R
import com.dscvit.handly.adapter.FilesAdapter
import com.dscvit.handly.model.Result
import com.dscvit.handly.model.collection.DeleteCollectionRequest
import com.dscvit.handly.model.collection.UpdateCollection
import com.dscvit.handly.model.files.FileViewRequest
import com.dscvit.handly.model.files.UpdateFile
import com.dscvit.handly.util.*
import com.github.ybq.android.spinkit.style.Circle
import com.github.ybq.android.spinkit.style.Wave
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_files.*
import kotlinx.android.synthetic.main.modify_collection_alert.view.*
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

        getFiles(fileViewModel, collectionID ?: "", filesAdapter)

        filesRecyclerView.addOnItemLongClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                val dialogBuilder = MaterialAlertDialogBuilder(this@FilesActivity).create()
                val dialogView = layoutInflater.inflate(R.layout.modify_collection_alert, null)
                dialogView.modify_progress.setIndeterminateDrawable(Circle())
                dialogView.modify_progress.hide()
                dialogView.modify_name.setText(filesAdapter.filesList[position].inputDetails.name)
                dialogView.modify_name.hideSoftKeyboardOnFocusLostEnabled(true)

                dialogView.modify_button.setOnClickListener {
                    if (dialogView.modify_name.text.isNotBlank()) {
                        val updateFile = UpdateFile(
                            dialogView.modify_name.text.toString().trim()
                        )
                        fileViewModel.updateFile(
                            filesAdapter.filesList[position].inputDetails.id,
                            updateFile
                        )
                            .observe(this@FilesActivity, Observer {
                                when (it) {
                                    is Result.Loading -> {
                                        dialogView.modify_name.hide()
                                        dialogView.modify_cancel.hide()
                                        dialogView.modify_button.hide()
                                        dialogView.modify_delete.hide()
                                        dialogView.modify_title.hide()
                                        dialogView.modify_progress.show()
                                    }
                                    is Result.Success -> {
                                        getFiles(fileViewModel, collectionID?:"", filesAdapter)
                                        dialogBuilder.dismiss()
                                    }
                                    is Result.Error -> {
                                        Log.d("esh", it.message!!)
                                        shortToast("Oops something went wrong")

                                        dialogView.modify_name.show()
                                        dialogView.modify_cancel.show()
                                        dialogView.modify_button.show()
                                        dialogView.modify_delete.show()
                                        dialogView.modify_title.show()
                                        dialogView.modify_progress.hide()
                                    }
                                }
                            })
                    } else {
                        shortToast("Name can't be empty")
                    }
                }

                dialogView.modify_delete.setOnClickListener {
                    fileViewModel.deleteFile(filesAdapter.filesList[position].inputDetails.id)
                        .observe(this@FilesActivity, Observer {
                            when (it) {
                                "Loading" -> {
                                    dialogView.modify_name.hide()
                                    dialogView.modify_cancel.hide()
                                    dialogView.modify_button.hide()
                                    dialogView.modify_delete.hide()
                                    dialogView.modify_title.hide()
                                    dialogView.modify_progress.show()
                                }
                                "Success" -> {
                                    getFiles(fileViewModel, collectionID ?: "", filesAdapter)
                                    dialogBuilder.dismiss()
                                }
                                "Failed" -> {
                                    shortToast("Oops something went wrong")

                                    dialogView.modify_name.show()
                                    dialogView.modify_cancel.show()
                                    dialogView.modify_button.show()
                                    dialogView.modify_delete.show()
                                    dialogView.modify_title.show()
                                    dialogView.modify_progress.hide()
                                }
                                else -> {
                                    Log.d("esh", it)
                                }
                            }
                        })
                }

                dialogView.modify_cancel.setOnClickListener {
                    dialogBuilder.dismiss()
                }

                dialogBuilder.setView(dialogView)
                dialogBuilder.setCancelable(false)
                dialogBuilder.show()
            }
        })
    }

    private fun getFiles(filesViewModel: FilesViewModel, id: String, filesAdapter: FilesAdapter) {
        val requestBody = FileViewRequest(id)
        filesViewModel.getFiles(requestBody).observe(this, Observer {
            when (it) {
                is Result.Loading -> {
                    file_fab.hide()
                    filesRecyclerView.hide()
                    file_progress.show()
                }
                is Result.Success -> {
                    filesRecyclerView.show()
                    file_progress.hide()
                    file_fab.show()

                    filesAdapter.updateFiles(it.data!!)
                }
                is Result.Error -> {
                    filesRecyclerView.hide()
                    file_progress.hide()
                    file_fab.show()
                    Log.d("esh", "Error Ono")
                }
            }
        })
    }
}