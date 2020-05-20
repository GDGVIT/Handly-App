package com.dscvit.handly.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager

import com.dscvit.handly.R
import com.dscvit.handly.adapter.CollectionsAdapter
import com.dscvit.handly.util.hide
import com.github.ybq.android.spinkit.style.Wave
import kotlinx.android.synthetic.main.fragment_collections.*


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

        val collectionAdapter = CollectionsAdapter()
        collection_recycler_view.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            adapter = collectionAdapter
        }
    }

}
