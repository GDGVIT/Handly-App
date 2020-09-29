package com.dscvit.handly.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.dscvit.handly.R
import com.dscvit.handly.adapter.CollectionsAdapter
import com.dscvit.handly.model.Result
import com.dscvit.handly.model.collection.CreateCollectionRequest
import com.dscvit.handly.model.collection.DeleteCollectionRequest
import com.dscvit.handly.model.collection.UpdateCollection
import com.dscvit.handly.ui.files.FilesActivity
import com.dscvit.handly.util.*
import com.github.ybq.android.spinkit.style.Circle
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.add_collection_alert.view.*
import kotlinx.android.synthetic.main.fragment_collections.*
import kotlinx.android.synthetic.main.modify_collection_alert.view.*
import org.koin.android.viewmodel.ext.android.sharedViewModel


class CollectionsFragment : Fragment() {

    private val TAG = this.javaClass.simpleName

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_collections, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        collection_progress.hide()
        collection_progress.setIndeterminateDrawable(Circle())

        noContentView.hide()

        val homeViewModel by sharedViewModel<HomeViewModel>()

        val collectionAdapter = CollectionsAdapter()
        collection_recycler_view.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)
            adapter = collectionAdapter
        }

        getCollection(homeViewModel, collectionAdapter)

        collection_recycler_view.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                val intent = Intent(requireContext(), FilesActivity::class.java)
                intent.putExtra("collectionID", collectionAdapter.collectionList[position].id)
                intent.putExtra("collectionName", collectionAdapter.collectionList[position].name)
                startActivity(intent)
            }
        })

        collection_recycler_view.addOnItemLongClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                val dialogBuilder = MaterialAlertDialogBuilder(requireContext()).create()
                val dialogView = layoutInflater.inflate(R.layout.modify_collection_alert, null)
                dialogView.modify_progress.setIndeterminateDrawable(Circle())
                dialogView.modify_progress.hide()
                dialogView.modify_name.setText(collectionAdapter.collectionList[position].name)
                dialogView.modify_name.hideSoftKeyboardOnFocusLostEnabled(true)

                dialogView.modify_button.setOnClickListener {
                    if (dialogView.modify_name.text.isNotBlank()) {
                        val updateCollection = UpdateCollection(
                            collectionAdapter.collectionList[position].id,
                            dialogView.modify_name.text.toString().trim()
                        )
                        homeViewModel.updateCollections(updateCollection)
                            .observe(viewLifecycleOwner, Observer {
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
                                        getCollection(homeViewModel, collectionAdapter)
                                        dialogBuilder.dismiss()
                                    }
                                    is Result.Error -> {
                                        Log.d(TAG, it.message!!)
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
                    val deleteCollectionRequest = DeleteCollectionRequest(
                        collectionAdapter.collectionList[position].id
                    )
                    homeViewModel.deleteCollection(deleteCollectionRequest)
                        .observe(viewLifecycleOwner, Observer {
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
                                    getCollection(homeViewModel, collectionAdapter)
                                    dialogBuilder.dismiss()
                                }
                                "Failed" -> {
                                    shortToast("Oops, something went wrong")

                                    dialogView.modify_name.show()
                                    dialogView.modify_cancel.show()
                                    dialogView.modify_button.show()
                                    dialogView.modify_delete.show()
                                    dialogView.modify_title.show()
                                    dialogView.modify_progress.hide()
                                }
                                else -> {
                                    Log.d(TAG, it)
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

        collection_fab.setOnClickListener {
            val dialogBuilder = MaterialAlertDialogBuilder(requireContext()).create()
            val dialogView = layoutInflater.inflate(R.layout.add_collection_alert, null)
            dialogView.create_progress.setIndeterminateDrawable(Circle())
            dialogView.create_progress.hide()
            dialogView.create_name.hideSoftKeyboardOnFocusLostEnabled(true)

            dialogView.create_button.setOnClickListener {
                if (dialogView.create_name.text.isNotBlank()) {
                    val createCollectionRequest = CreateCollectionRequest(
                        dialogView.create_name.text.toString().trim()
                    )
                    homeViewModel.createCollection(createCollectionRequest)
                        .observe(viewLifecycleOwner, Observer {
                            when (it) {
                                is Result.Loading -> {
                                    dialogView.create_title.hide()
                                    dialogView.create_name.hide()
                                    dialogView.create_button.hide()
                                    dialogView.create_cancel.hide()
                                    dialogView.create_progress.show()
                                }
                                is Result.Success -> {
                                    getCollection(homeViewModel, collectionAdapter)
                                    dialogBuilder.dismiss()
                                }
                                is Result.Error -> {
                                    Log.d(TAG, it.message!!)
                                    shortToast("Oops something went wrong")

                                    dialogView.create_title.show()
                                    dialogView.create_name.show()
                                    dialogView.create_button.show()
                                    dialogView.create_cancel.show()
                                    dialogView.create_progress.hide()
                                }
                            }
                        })
                } else {
                    shortToast("Name can't be empty")
                }
            }

            dialogView.create_cancel.setOnClickListener {
                dialogBuilder.dismiss()
            }

            dialogBuilder.setView(dialogView)
            dialogBuilder.setCancelable(false)
            dialogBuilder.show()
        }
    }

    private fun getCollection(
        homeViewModel: HomeViewModel,
        collectionsAdapter: CollectionsAdapter
    ) {
        homeViewModel.getCollections().observe(viewLifecycleOwner, Observer {
            when (it) {
                is Result.Loading -> {
                    collection_recycler_view.hide()
                    collection_progress.show()
                    noContentView.hide()
                    collection_fab.hide()
                }
                is Result.Success -> {
                    val collections = it.data!!
                    if (collections.isEmpty()) {
                        collection_recycler_view.hide()
                        noContentView.show()
                    } else {
                        collectionsAdapter.updateCollections(collections)
                        noContentView.hide()
                        collection_recycler_view.show()
                    }
                    collection_progress.hide()
                    collection_fab.show()
                }
                is Result.Error -> {
                    Log.d(TAG, it.message!!)
                    shortToast("Oops, something went wrong!")
                    collection_progress.hide()
                    collection_fab.show()
                }
            }
        })
    }

}
