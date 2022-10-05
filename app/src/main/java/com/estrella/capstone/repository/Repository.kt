package com.estrella.capstone.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.estrella.capstone.Constants
import com.estrella.capstone.database.ExercisesDatabase
import com.estrella.capstone.database.TemplatesDatabase
import com.estrella.capstone.entities.ExercisesData
import com.estrella.capstone.entities.TemplatesData
import com.estrella.capstone.network.ExercisesApi
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
    val allBodyParts: List<String> =
        listOf("Body Parts") + exercisesDatabase.exercisesDao.getAllBodyParts()

    val allEquipments: List<String> =
        listOf("Equipments") + exercisesDatabase.exercisesDao.getAllEquipments()

    val allTargets: List<String> =
        listOf("Targets") + exercisesDatabase.exercisesDao.getAllTargets()

    init {
        getAllExercisesList()
    }

    /**
     * More general way of filtering all three types (Body Parts, Equipments, Targets)
     */
    fun filterExercises(name: String, bodyPart: String, equipment: String, target: String) {
        var thisName = name.ifEmpty { "%" }
        var thisBodyPart = bodyPart.ifEmpty { "%" }
        var thisEquipment = equipment.ifEmpty { "%" }
        var thisTarget = target.ifEmpty { "%" }
        _exercisesList.postValue(
            exercisesDatabase.exercisesDao
                .filterExercises(thisName, thisBodyPart, thisEquipment, thisTarget)
        )
    }

    /**
     * Fill the exercisesList with all the exercise from the database
     */
    fun getAllExercisesList() {
        _exercisesList.postValue(exercisesDatabase.exercisesDao.getExercises())
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
                exercisesDatabase.exercisesDao.insertExercises(newExercise)
            }
        }
    }

}