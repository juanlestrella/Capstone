package com.example.capstone.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.capstone.database.getExercisesDataBase
import com.example.capstone.database.getTemplatesDatabase
import com.example.capstone.entities.ExercisesData
import com.example.capstone.entities.TemplatesData
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

    private val exercisesDatabase = getExercisesDataBase(application)
    private val templatesDatabase = getTemplatesDatabase(application)
    private val repository = Repository(exercisesDatabase, templatesDatabase)

    val exercisesList = repository.exercisesList
    val templates = repository.templatesList
    private val finalExercisesList = mutableListOf<ExercisesData>()

    // mutableListOf(TemplatesData("title", mutableListOf("ex1", "ex2")))

    // 1) Change finalExercisesList to be a Data class
    // -> TemplatesData(title, mutableListOf<String>())
    // 2) problem: how to get the title from user
    // 3) Create a fun() to add a new templates (insertNewTemplates)

    fun insertNewTemplate(newTemplate: TemplatesData){
        // TODO: Do I need to check anything before adding the new template?
        repository.insertNewTemplate(newTemplate)
    }

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
                Log.i("ExercisesViewModel",status.value.toString())
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