package com.example.capstone.exercises

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstone.R
import com.example.capstone.databinding.ListItemExercisesBinding
import com.example.capstone.entities.ExercisesData

class ExercisesAdapter :
    ListAdapter<ExercisesData, ExercisesAdapter.ViewHolder>(ExercisesDiffCallback()) {


    class ViewHolder(private var binding: ListItemExercisesBinding)
        : RecyclerView.ViewHolder(binding.root){
        fun bind(data: ExercisesData) {
            binding.exercises = data
            binding.executePendingBindings()
            // add glide here for gif
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ListItemExercisesBinding.inflate(
            LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(getItem(position))
    }

}

class ExercisesDiffCallback : DiffUtil.ItemCallback<ExercisesData>(){

    override fun areItemsTheSame(oldItem: ExercisesData, newItem: ExercisesData): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ExercisesData, newItem: ExercisesData): Boolean {
        return oldItem.id == newItem.id
    }

}
