package com.estrella.capstone.profiledetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.estrella.capstone.databinding.ListItemProfileDetailsBinding
import com.estrella.capstone.entities.ExercisesData

class ProfileDetailsAdapter :
    ListAdapter<ExercisesData, ProfileDetailsAdapter.ViewHolder>(ProfileDetailDiffCallBack()) {

    inner class ViewHolder(private val binding: ListItemProfileDetailsBinding)
        : RecyclerView.ViewHolder(binding.root){
        fun bind(data: ExercisesData) {
            binding.exercises = data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
    : ProfileDetailsAdapter.ViewHolder {
        return ViewHolder(ListItemProfileDetailsBinding.inflate(
            LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ProfileDetailsAdapter.ViewHolder, position: Int) {
        return holder.bind(getItem(position))
    }
}

class ProfileDetailDiffCallBack : DiffUtil.ItemCallback<ExercisesData>() {
    override fun areItemsTheSame(oldItem: ExercisesData, newItem: ExercisesData): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ExercisesData, newItem: ExercisesData): Boolean {
        return oldItem.id == newItem.id
    }


}
