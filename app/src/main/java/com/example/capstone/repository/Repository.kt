package com.example.capstone.repository

import androidx.lifecycle.LiveData
import com.example.capstone.Constants
import com.example.capstone.database.ExercisesDatabase
import com.example.capstone.database.TemplatesDatabase
import com.example.capstone.entities.ExercisesData
import com.example.capstone.entities.TemplatesData
import com.example.capstone.network.ExercisesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(
    private val exercisesDatabase: ExercisesDatabase,
    private val templatesDatabase: TemplatesDatabase
) {

    val exercisesList: LiveData<List<ExercisesData>> =
        exercisesDatabase.exercisesDao.getExercises()

    val templatesList: LiveData<List<TemplatesData>> = templatesDatabase.templatesDao.getTemplates()

    /**
     * Delete TemplatesData from Template's Room
     */
    fun deleteTemplate(template: TemplatesData) {
        templatesDatabase.templatesDao.deleteTemplate(template)
    }
    /**
     * Insert new TemplatesData to Template's Room
     */
    fun insertNewTemplate(newTemplates: TemplatesData) {
        templatesDatabase.templatesDao.insertTemplates(newTemplates)
    }

    /**
     * Used in the SharedViewModel to refresh the data
     */
    suspend fun refreshData() {
        withContext(Dispatchers.IO) {
            val newListExercises =
                ExercisesApi.retrofitService.getExercises(Constants.RAPID_HOST, Constants.KEY)
            newListExercises.forEach { newExercise ->
                //Log.i("Repository", newExercise.toString())
                exercisesDatabase.exercisesDao.insertExercises(newExercise)
            }
        }
    }

}