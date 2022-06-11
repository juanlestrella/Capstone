package com.example.capstone.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone.databinding.ListItemProfileBinding
import com.example.capstone.entities.ExercisesData
import com.example.capstone.entities.TemplatesData
import com.example.capstone.viewmodel.SharedViewModel

class ProfileAdapter() :
    ListAdapter<TemplatesData, ProfileAdapter.ViewHolder>(ProfileDiffCallback()) {

//    override fun submitList(list: List<TemplatesData>?) {
//        super.submitList(list?.let { ArrayList(it)})
//    }

    inner class ViewHolder(private var binding: ListItemProfileBinding)
        : RecyclerView.ViewHolder(binding.root){
        fun bind(template: TemplatesData) {
            binding.templates = template
            binding.recyclerListItemProfile.layoutManager =
                LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)
            binding.recyclerListItemProfile.adapter = ProfileInnerAdapter()
            (binding.recyclerListItemProfile.adapter as ProfileInnerAdapter)
                .submitList(template.exercises)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ListItemProfileBinding.inflate(
            LayoutInflater.from(parent.context)
        ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(getItem(position))
    }
}

class ProfileDiffCallback : DiffUtil.ItemCallback<TemplatesData>(){
    override fun areItemsTheSame(oldItem: TemplatesData, newItem: TemplatesData): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: TemplatesData, newItem: TemplatesData): Boolean {
        return oldItem.idTemplatesData == newItem.idTemplatesData
    }

}
