package com.example.capstone.exercises

import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstone.R
import com.example.capstone.databinding.ListItemExercisesBinding
import com.example.capstone.entities.ExercisesData
import com.example.capstone.viewmodel.SharedViewModel
import kotlin.coroutines.coroutineContext

class ExercisesAdapter() :
    ListAdapter<ExercisesData, ExercisesAdapter.ViewHolder>(ExercisesDiffCallback()) {

    private var checkBoxList = mutableListOf<ExercisesData>()

    /**
     * RETURN: List of ExercisesData with checked box
     */
    fun returnCheckBoxList() : MutableList<ExercisesData>{
        return checkBoxList
    }

    inner class ViewHolder(private var binding: ListItemExercisesBinding)
        : RecyclerView.ViewHolder(binding.root){

        fun bind(data: ExercisesData) {
            binding.exercises = data

            binding.checkBox.isChecked = data.isChecked // avoid reusing checked boxes

            /*
                if current box is checked, then add current data
                to mutable list of ExercisesData. Otherwise,
                the current box is unchecked so then
                remove the current data from the list.
             */
            binding.checkBox.setOnCheckedChangeListener{
                button, isChecked ->
                    if(isChecked){
                        checkBoxList.add(data)
                    }else{
                        checkBoxList.remove(data)
                    }
            }
            binding.executePendingBindings()
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
