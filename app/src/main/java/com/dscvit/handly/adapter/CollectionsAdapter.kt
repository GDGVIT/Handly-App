package com.dscvit.handly.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dscvit.handly.R
import com.dscvit.handly.model.collection.Collection
import kotlinx.android.synthetic.main.collection_item.view.*

class CollectionsAdapter : RecyclerView.Adapter<CollectionsAdapter.CollectionViewHolder>() {

    var collectionList: MutableList<Collection> = mutableListOf()

    fun updateCollections(newCollections: List<Collection>) {
        collectionList = newCollections as MutableList<Collection>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CollectionViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.collection_item,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        holder.bind(collectionList[position])
    }

    override fun getItemCount() = collectionList.size

    class CollectionViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val folderName = view.folder_name

        fun bind(collection: Collection) {
            folderName.text = collection.name
        }
    }

}