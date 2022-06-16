package com.example.capstone.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone.databinding.ListItemProfileInnerBinding
import com.example.capstone.entities.ExercisesData

class ProfileInnerAdapter() :
    ListAdapter<ExercisesData, ProfileInnerAdapter.ViewHolder>(ProfileInnerDiffCallback()) {

    class ViewHolder(private var binding: ListItemProfileInnerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ExercisesData?) {
            binding.exercises = item
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemProfileInnerBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: ProfileInnerAdapter.ViewHolder, position: Int) {
        return holder.bind(getItem(position))
    }
}

class ProfileInnerDiffCallback : DiffUtil.ItemCallback<ExercisesData>() {
    override fun areItemsTheSame(oldItem: ExercisesData, newItem: ExercisesData): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ExercisesData, newItem: ExercisesData): Boolean {
        return oldItem.id == newItem.id
    }

}
