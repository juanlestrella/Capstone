package com.estrella.capstone.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.estrella.capstone.R
import com.estrella.capstone.databinding.ListItemProfileBinding
import com.estrella.capstone.entities.TemplatesData
import com.estrella.capstone.viewmodel.SharedViewModel

class ProfileAdapter(private val sharedViewModel: SharedViewModel) :
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
            binding.templates = data
            /**
             * Create an inner RecyclerView using ProfileInnerAdapter
             */
            binding.recyclerListItemProfile.layoutManager =
                LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)
            binding.recyclerListItemProfile.adapter = ProfileInnerAdapter()
            (binding.recyclerListItemProfile.adapter as ProfileInnerAdapter)
                .submitList(data.exercises)

            /**
             * deletes the current template
             */
            binding.deleteButton.setOnClickListener {
                sharedViewModel.deleteTemplate(data)
            }
            /**
             * edit the current template:
             * 1) navigate to ProfileDetails Fragment
             * 2) pass current TemplatesData to ProfileDetails Fragment
             */
            binding.editButton.setOnClickListener {
                /**
                 * Needs to delete current Template before navigating
                 * In ProfileDetails,
                 *      if user press back button, recreate this template
                 *      elif user press Done button, create a new template with new exercises
                 */
                // deletes previous exercise list on ProfileDetails
                sharedViewModel.clearFinalExercisesList()
                // pass current TemplatesData to ProfileDetails
                sharedViewModel.setCheckedExercisesList(data.exercises.toMutableList())
                // let the profiledetails that a template is being edit
                sharedViewModel.isEditing = true
                // save current data, if new edited template is saved then delete this
                sharedViewModel.currentEditingTemplate = data
                // navigate to ProfileDetail Fragment
                binding.root.findNavController().navigate(R.id.profileDetailFragment)
            }
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
