package com.dscvit.handly.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dscvit.handly.R
import com.dscvit.handly.model.files.FileViewResponse
import com.dscvit.handly.model.files.InputDetails
import kotlinx.android.synthetic.main.file_item.view.*

class FilesAdapter : RecyclerView.Adapter<FilesAdapter.FileViewHolder>() {

    var filesList: MutableList<FileViewResponse> = mutableListOf()

    fun updateFiles(newFiles: List<FileViewResponse>) {
        filesList = newFiles as MutableList<FileViewResponse>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FileViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.file_item,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        holder.bind(filesList[position].inputDetails)
    }

    override fun getItemCount(): Int = filesList.size

    class FileViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val fileName = view.file_name

        fun bind(inputDetails: InputDetails) {
            fileName.text = inputDetails.name
        }
    }

}