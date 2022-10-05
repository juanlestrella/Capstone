package com.estrella.capstone.exercises

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.estrella.capstone.databinding.ListItemExercisesBinding
import com.estrella.capstone.entities.ExercisesData

class ExercisesAdapter() :
    ListAdapter<ExercisesData, ExercisesAdapter.ViewHolder>(ExercisesDiffCallback()){
    private val checkBoxList = mutableListOf<ExercisesData>()

    /**
     * RETURN: List of ExercisesData with checked box
     */
    fun returnCheckBoxList(): MutableList<ExercisesData> {
        return checkBoxList
    }

    /**
     * Add an exercise to checkboxList
     */
    fun addExerciseToCheckBoxList(exercisesData: ExercisesData): Boolean{
        return checkBoxList.add(exercisesData)
    }

    inner class ViewHolder(private var binding: ListItemExercisesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: ExercisesData) {
            binding.exercises = data

            // prevent recyclerview from reusing checked boxes
            binding.checkBox.setOnCheckedChangeListener(null)
            // checks the box if the exercise is in the list of checked boxes
            binding.checkBox.isChecked = checkBoxList.contains(binding.exercises)
            /**
            If current box is checked, then add current data
            to mutable list of ExercisesData. Otherwise,
            the current box is unchecked so then
            remove the current data from the list.
             **/
            binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    if(binding.exercises !in checkBoxList){
                        checkBoxList.add(data)
                    }
                } else {
                    checkBoxList.remove(data)
                }
            }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemExercisesBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(getItem(position))
    }

}

class ExercisesDiffCallback : DiffUtil.ItemCallback<ExercisesData>() {

    override fun areItemsTheSame(oldItem: ExercisesData, newItem: ExercisesData): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ExercisesData, newItem: ExercisesData): Boolean {
        return oldItem.id == newItem.id
    }

}
