package com.dscvit.handly.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager

import com.dscvit.handly.R
import com.dscvit.handly.adapter.CollectionsAdapter
import com.dscvit.handly.model.Result
import com.dscvit.handly.util.hide
import com.dscvit.handly.util.show
import com.github.ybq.android.spinkit.style.Wave
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
                }
                Result.Status.SUCCESS -> {
                    val collections = it.data!!
                    collectionsAdapter.updateCollections(collections)
                    collection_recycler_view.show()
                    collection_progress.hide()
                }
                Result.Status.ERROR -> {
                    Log.d("esh", it.message!!)
                    collection_progress.hide()
                }
            }
        })
    }

}
