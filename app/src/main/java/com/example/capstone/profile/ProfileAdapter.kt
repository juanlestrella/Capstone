package com.example.capstone.profile

import android.app.Application
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone.databinding.ListItemProfileBinding
import com.example.capstone.entities.TemplatesData
import com.example.capstone.viewmodel.SharedViewModel

class ProfileAdapter() :
    ListAdapter<TemplatesData, ProfileAdapter.ViewHolder>(ProfileDiffCallback()) {

    inner class ViewHolder(private var binding: ListItemProfileBinding) :
        RecyclerView.ViewHolder(binding.root) {
        /**
         * 1) Assign data to binding.templates
         * 2) Assign LinearLayoutManager to the inner recycler view
         * 3) Submit the list of exercises from data to the inner recycler view's adapter
         * 4) Add click listener for delete and edit buttons
         */
        fun bind(data: TemplatesData) {
            val viewModel = SharedViewModel(Application())

            binding.templates = data
            /**
             * deletes the current template
             */
            binding.deleteButton.setOnClickListener {
                viewModel.deleteTemplate(data)
            }
            /**
             * edit the current template:
             * 1) navigate to ProfileDetails Fragment
             * 2) pass current TemplatesData to ProfileDetails Fragment
             */
            binding.editButton.setOnClickListener {
                
            }

            binding.recyclerListItemProfile.layoutManager =
                LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)
            binding.recyclerListItemProfile.adapter = ProfileInnerAdapter()
            (binding.recyclerListItemProfile.adapter as ProfileInnerAdapter)
                .submitList(data.exercises)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemProfileBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(getItem(position))
    }
}

class ProfileDiffCallback : DiffUtil.ItemCallback<TemplatesData>() {
    override fun areItemsTheSame(oldItem: TemplatesData, newItem: TemplatesData): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: TemplatesData, newItem: TemplatesData): Boolean {
        return oldItem.idTemplatesData == newItem.idTemplatesData
    }

}
