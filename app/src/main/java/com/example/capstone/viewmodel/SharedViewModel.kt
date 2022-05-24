package com.example.capstone.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.capstone.database.getDataBase
import com.example.capstone.entities.ExercisesData
import com.example.capstone.repository.Repository
import kotlinx.coroutines.launch
import java.lang.Exception

/**
 * Use AndroidViewModel(application) to give the
 * SharedViewModel class an application parameter
 * to pass to the database
 */
class SharedViewModel(application: Application) : AndroidViewModel(application) {

    private val _status = MutableLiveData<String>()
    val status : LiveData<String>
        get() = _status

    private val _checkedExercisesList = MutableLiveData<List<ExercisesData>>()
    val checkedExercisesList : LiveData<List<ExercisesData>>
        get() = _checkedExercisesList

    private val database = getDataBase(application)
    private val repository = Repository(database)
    val exercisesList = repository.exercisesList

    private val finalExercisesList = mutableListOf<ExercisesData>()

    fun clearFinalExercisesList(){
        finalExercisesList.clear()
    }

    /**
     * Allows ProfileDetailsFragment to keep adding exercises without
     * resetting the value everytime "ADD EXERCISES" button is clicked
     * RETURN: MutableList of ExercisesData
     */
    fun returnFinalExercisesList(): MutableList<ExercisesData> {
        _checkedExercisesList.value?.forEach {
            if(it !in finalExercisesList){
                finalExercisesList.add(it)
            }
        }
        return finalExercisesList
    }

    /**
     * Update the value of checkedExercisesList with the value
     * from ExercisesAdapter.returnCheckBoxList()
     */
    fun setCheckedExercisesList(dataList: MutableList<ExercisesData>){
        _checkedExercisesList.postValue(dataList)
    }

    /**
     * Change the value of all
     * ExercisesData.isChecked to false
     */
    fun checkBoxAllFalse(){
        exercisesList.value?.forEach { exercise ->
            if(exercise.isChecked){
                exercise.isChecked = false
            }
        }
    }

    init {
        /**
         * Refresh the Data and make sure API is connected
         */
        viewModelScope.launch {
            try{
                repository.refreshData()
                _status.postValue("Connected")
                Log.i("ExercisesViewModel", repository.exercisesList.value?.get(0).toString())
            }catch (e: Exception){
                _status.postValue("Failure: " + e.message)
            }
        }
    }

//    class Factory(val application: Application) : ViewModelProvider.Factory {
//        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//            if (modelClass.isAssignableFrom(SharedViewModel::class.java)) {
//                @Suppress("UNCHECKED_CAST")
//                return SharedViewModel(application) as T
//            }
//            throw IllegalArgumentException("Unable to construct ViewModel")
//        }
//    }
}