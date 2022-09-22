package com.example.capstone.repository

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

class Repository(
    private val exercisesDatabase: ExercisesDatabase,
    private val templatesDatabase: TemplatesDatabase
) {

    private val _exercisesList = MutableLiveData<List<ExercisesData>>()
    val exercisesList: LiveData<List<ExercisesData>>
        get() = _exercisesList

    val templatesList: LiveData<List<TemplatesData>> =
        templatesDatabase.templatesDao.getTemplates()

    /**
     * Gets all the bodypart names
     */
    val allBodyParts: List<String> = listOf("Body Parts") + exercisesDatabase.exercisesDao.getAllBodyParts()
    val allEquipments: List<String> = listOf("Equipments") + exercisesDatabase.exercisesDao.getAllEquipments()
    val allTargets: List<String> = listOf("Targets") + exercisesDatabase.exercisesDao.getAllTargets()

    init {
        getAllExercisesList()
    }
    /**
     * Filter exerciseList by selected bodypart
     */
    fun filterBodyPart(bodyPart: String){
        _exercisesList.postValue(exercisesDatabase.exercisesDao.filterBodyPart(bodyPart))
    }

    /**
     * Fill the exercisesList with all the exercise from the database
     */
    fun getAllExercisesList(){
        _exercisesList.postValue(exercisesDatabase.exercisesDao.getExercises())
    }

    /**
     * Search Bar: Query exercises table with exercise name
     */
    fun searchExercise(name: String) {
        _exercisesList.postValue(exercisesDatabase.exercisesDao.findExerciseWithName(name))
    }

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