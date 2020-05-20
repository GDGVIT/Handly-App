package com.dscvit.handly.ui.home

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
import com.dscvit.handly.util.hide
import com.dscvit.handly.util.shortToast
import com.dscvit.handly.util.show
import com.github.ybq.android.spinkit.style.Circle
import com.github.ybq.android.spinkit.style.Wave
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.add_collection_alert.view.*
import kotlinx.android.synthetic.main.fragment_collections.*
import org.koin.android.viewmodel.ext.android.sharedViewModel


class CollectionsFragment : Fragment() {

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
        collection_progress.setIndeterminateDrawable(Wave())

        val homeViewModel by sharedViewModel<HomeViewModel>()

        val collectionAdapter = CollectionsAdapter()
        collection_recycler_view.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)
            adapter = collectionAdapter
        }

        getCollection(homeViewModel, collectionAdapter)

        collection_fab.setOnClickListener {
            val dialogBuilder = MaterialAlertDialogBuilder(requireContext()).create()
            val inflater = this.layoutInflater
            val dialogView = inflater.inflate(R.layout.add_collection_alert, null)
            dialogView.create_progress.setIndeterminateDrawable(Circle())
            dialogView.create_progress.hide()

            dialogView.create_button.setOnClickListener {
                if (dialogView.create_name.text.isNotBlank()) {
                    val createCollectionRequest = CreateCollectionRequest(
                        dialogView.create_name.text.toString().trim()
                    )
                    homeViewModel.createCollection(createCollectionRequest)
                        .observe(viewLifecycleOwner, Observer {
                            when (it.status) {
                                Result.Status.LOADING -> {
                                    dialogView.create_title.hide()
                                    dialogView.create_name.hide()
                                    dialogView.create_button.hide()
                                    dialogView.create_cancel.hide()
                                    dialogView.create_progress.show()
                                }
                                Result.Status.SUCCESS -> {
                                    getCollection(homeViewModel, collectionAdapter)
                                    dialogBuilder.dismiss()
                                }
                                Result.Status.ERROR -> {
                                    Log.d("esh", it.message!!)
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
                    shortToast("Name cant be empty")
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
            when (it.status) {
                Result.Status.LOADING -> {
                    collection_recycler_view.hide()
                    collection_progress.show()
                    collection_fab.hide()
                }
                Result.Status.SUCCESS -> {
                    val collections = it.data!!
                    collectionsAdapter.updateCollections(collections)
                    collection_recycler_view.show()
                    collection_progress.hide()
                    collection_fab.show()
                }
                Result.Status.ERROR -> {
                    Log.d("esh", it.message!!)
                    collection_progress.hide()
                }
            }
        })
    }

}
