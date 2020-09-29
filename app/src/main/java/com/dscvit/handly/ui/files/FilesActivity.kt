package com.dscvit.handly.ui.files

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.dscvit.handly.R
import com.dscvit.handly.adapter.FilesAdapter
import com.dscvit.handly.model.Result
import com.dscvit.handly.model.files.FileViewRequest
import com.dscvit.handly.model.files.UpdateFile
import com.dscvit.handly.util.*
import com.github.ybq.android.spinkit.style.Circle
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_files.*
import kotlinx.android.synthetic.main.modify_collection_alert.view.*
import kotlinx.android.synthetic.main.notification_alert.view.*
import kotlinx.android.synthetic.main.upload_file_alert.view.*
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class FilesActivity : AppCompatActivity() {
    lateinit var collectionID: String
    val fileViewModel by viewModel<FilesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_files)

        file_progress.hide()
        file_progress.setIndeterminateDrawable(Circle())

        val filesAdapter = FilesAdapter()
        filesRecyclerView.apply {
            layoutManager =
                GridLayoutManager(this@FilesActivity, 3, GridLayoutManager.VERTICAL, false)
            adapter = filesAdapter
        }

        val extras = intent.extras
        collectionID = extras?.getString("collectionID") ?: ""
        val collectionName = extras?.getString("collectionName")

        toolbar?.title = collectionName
        toolbar.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_back)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        fileRefresh.setColorSchemeColors(Color.parseColor("#63a4ff"))

        fileRefresh.setOnRefreshListener {
            refreshFiles(fileViewModel, collectionID, filesAdapter)
        }

        getFiles(fileViewModel, collectionID, filesAdapter)

        filesRecyclerView.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                val url = filesAdapter.filesList[position].awsUrl
                val browserIntent = Intent(Intent.ACTION_VIEW)
                browserIntent.data = Uri.parse(url)
                val intent = Intent.createChooser(browserIntent, "Choose Viewer")
                startActivity(intent)
            }
        })

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
                                        getFiles(fileViewModel, collectionID, filesAdapter)
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

        file_fab.setOnClickListener {
            val intent = Intent()
            intent.type = "*/*"
            val mimeTypes = arrayOf(
                "application/msword",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
            )
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Choose a file"), 111)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 111 && resultCode == RESULT_OK) {
            val uri: Uri = data?.data!!
            val parcelFileDescriptor =
                contentResolver.openFileDescriptor(uri, "r", null) ?: return

            val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
            val file = File(cacheDir, contentResolver.getFileName(uri))
            val outputStream = FileOutputStream(file)
            inputStream.copyTo(outputStream)

            if (file.length() < 5000000) {
                val dialogBuilder = MaterialAlertDialogBuilder(this@FilesActivity).create()
                val dialogView = layoutInflater.inflate(R.layout.upload_file_alert, null)
                dialogView.upload_progress.setIndeterminateDrawable(Circle())
                dialogView.upload_progress.hide()
                dialogView.upload_name.hideSoftKeyboardOnFocusLostEnabled(true)

                dialogView.upload_file_filename.text = file.name

                dialogView.upload_cancel.setOnClickListener {
                    dialogBuilder.dismiss()
                }

                dialogView.upload_button.setOnClickListener {
                    if (dialogView.upload_name.text.isNotBlank()) {
                        val name = dialogView.upload_name.text.toString().trim()
                        fileViewModel.uploadFile(
                            collectionID.toRequestBody(),
                            name.toRequestBody(),
                            MultipartBody.Part.createFormData(
                                "file",
                                file.name,
                                file.asRequestBody()
                            )
                        ).observe(this@FilesActivity, Observer {
                            when (it) {
                                is Result.Loading -> {
                                    dialogView.upload_title.hide()
                                    dialogView.upload_name.hide()
                                    dialogView.upload_button.hide()
                                    dialogView.upload_cancel.hide()
                                    dialogView.upload_filename.hide()
                                    dialogView.upload_file_filename.hide()
                                    dialogView.upload_progress.show()
                                }
                                is Result.Success -> {
                                    dialogBuilder.dismiss()

                                    val notifDialogBuilder =
                                        MaterialAlertDialogBuilder(this@FilesActivity).create()
                                    val notifDialogView =
                                        layoutInflater.inflate(R.layout.notification_alert, null)

                                    notifDialogView.notify_okay_button.setOnClickListener {
                                        notifDialogBuilder.dismiss()
                                    }

                                    notifDialogBuilder.setView(notifDialogView)
                                    notifDialogBuilder.setCancelable(false)
                                    notifDialogBuilder.show()

                                    Log.d("esh", "Doneeeee")
                                }
                                is Result.Error -> {
                                    shortToast("Oops something went wrong")
                                    dialogView.upload_title.show()
                                    dialogView.upload_name.show()
                                    dialogView.upload_button.show()
                                    dialogView.upload_cancel.show()
                                    dialogView.upload_filename.show()
                                    dialogView.upload_file_filename.show()
                                    dialogView.upload_progress.hide()
                                    Log.d("esh", it.message!!)
                                }
                            }
                        })
                    } else {
                        shortToast("Name can't be empty")
                    }
                }

                dialogBuilder.setView(dialogView)
                dialogBuilder.setCancelable(false)
                dialogBuilder.show()
            } else {
                shortToast("File must be less than 5 MB")
            }
        }
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

    private fun refreshFiles(filesViewModel: FilesViewModel, id: String, filesAdapter: FilesAdapter) {
        val requestBody = FileViewRequest(id)
        filesViewModel.getFiles(requestBody).observe(this, Observer {
            when (it) {
                is Result.Loading -> {
                }
                is Result.Success -> {
                    fileRefresh.isRefreshing = false

                    filesAdapter.updateFiles(it.data!!)
                }
                is Result.Error -> {
                    fileRefresh.isRefreshing = false
                    shortToast("Oops, something went wrong!")
                    Log.d("esh", "Error Ono")
                }
            }
        })
    }
}