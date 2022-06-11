package com.example.capstone.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.capstone.Constants
import com.example.capstone.database.ExercisesDatabase
import com.example.capstone.database.TemplatesDatabase
import com.example.capstone.entities.ExercisesData
import com.example.capstone.entities.TemplatesData
import com.example.capstone.network.ExercisesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class Repository(
    private val exercisesDatabase: ExercisesDatabase,
    private val templatesDatabase: TemplatesDatabase) {

    val exercisesList: LiveData<List<ExercisesData>> =
        exercisesDatabase.exercisesDao.getExerises()

    val templatesList: List<TemplatesData> = templatesDatabase.templatesDao.getTemplates()

    fun insertNewTemplate(newTemplates: TemplatesData) {
        templatesDatabase.templatesDao.insertTemplates(newTemplates)
    }

    /**
     * Used in the SharedViewModel to refresh the data
     */
    suspend fun refreshData(){
        withContext(Dispatchers.IO){
            val newListExercises = ExercisesApi.retrofitService.getExercises(Constants.RAPID_HOST, Constants.KEY)
            newListExercises.forEach{
                newExercise ->
                //Log.i("Repository", newExercise.toString())
                exercisesDatabase.exercisesDao.insertExercises(newExercise)
            }
        }
    }

}